(function WritMailController(){
    events.SEND_MAIL = "send_mail";
    events.MAIL_SENT = "mail_sent";

    window.writeMailController = new function(){
        this.setAddressee = setAddressee;
    }

    $(document).ready(init);

    let addresseeId = null;
    let addresseeQueryTimeout = null;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEND_MAIL},
        sendMail
    ));

    function sendMail(){
        const subjectField = document.getElementById("subject");
        const addresseeField = document.getElementById("addressee");
        const messageField = document.getElementById("message");

        if(addresseeId == null){
            notificationService.showError(Localization.getAdditionalContent("addressee-empty"));
        }else if(subjectField.value.length == 0){
            notificationService.showError(Localization.getAdditionalContent("subject-empty"));
        }else if(subjectField.value.length > 100){
            notificationService.showError(Localization.getAdditionalContent("subject-too-long"));
        }else if(messageField.value.length == 0){
            notificationService.showError(Localization.getAdditionalContent("message-empty"));
        }else if(messageField.value.length > 4000){
            notificationService.showError(Localization.getAdditionalContent("message-too-long"));
        }else{
            const payload = {
                addresseeId: addresseeId,
                subject: subjectField.value,
                message: messageField.value
            };
            const request = new Request(HttpMethod.PUT, Mapping.SEND_MAIL, payload);
                request.processValidResponse = function(){
                    notificationService.showSuccess(Localization.getAdditionalContent("mail-sent"));
                    subjectField.value = "";
                    invalidateAddressee();
                    messageField.value = "";
                    addresseeField.value = "";
                    eventProcessor.processEvent(new Event(events.MAIL_SENT));
                    eventProcessor.processEvent(new Event(events.OPEN_MAIN_LISTS));
                }
            dao.sendRequestAsync(request);
        }
    }

    function invalidateAddressee(){
        document.getElementById("addressee").classList.remove("addressee-selected");
        addresseeId = null;
    }

    function showAddressees(){
        const container = document.getElementById("addressee-search-result");
            container.innerHTML = "";

        if(addresseeQueryTimeout != null){
            clearTimeout(addresseeQueryTimeout);
        }

        addresseeQueryTimeout = setTimeout(loadAddressees, getSearchResultTimeout());

        function loadAddressees(){
            const container = document.getElementById("addressee-search-result");
            const queryText = document.getElementById("addressee").value;
                if(queryText.length < 3){
                    $("#addressee-field-empty").show();
                    $("#addressee-not-found").hide();
                    return;
                }else{
                    $("#addressee-field-empty").hide();
                }

                const request = new Request(HttpMethod.POST, Mapping.ADDRESSEES, {value: queryText});
                    request.convertResponse = function(response){
                        return JSON.parse(response.body);
                    }
                    request.processValidResponse = function(addressees){
                        if(addressees.length == 0){
                            $("#addressee-not-found").show();
                            return;
                        }else{
                            $("#addressee-not-found").hide();
                        }

                        addressees.sort(function(a, b){
                            return a.characterName.localeCompare(b.characterName);
                        })

                        for(let aIndex in addressees){
                            container.appendChild(createAddressee(addressees[aIndex]));
                        }
                    }
                dao.sendRequestAsync(request);

            function createAddressee(addressee){
                const container = document.createElement("DIV");
                    container.classList.add("addressee");
                    container.innerHTML = addressee.characterName;

                    container.onclick = function(){
                        setAddressee(addressee);
                    }
                return container;
            }
        }
    }

    function setAddressee(addressee){
        addresseeId = addressee.characterId;
        const inputField = document.getElementById("addressee");
            inputField.value = addressee.characterName;
            inputField.classList.add("addressee-selected");
    }

    function init(){
        $("#addressee-field-empty").hide();
        $("#addressee-not-found").hide();

        $("#addressee").focusin(function(){
            showAddressees();
            $("#addressee-list").fadeIn();
        });

        $("#addressee").focusout(function(){
            $("#addressee-list").fadeOut();
        });

        $("#addressee").keyup(function(){
            invalidateAddressee();
            showAddressees();
        });
    }
})();