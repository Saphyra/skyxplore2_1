(function WritMailController(){
    $(document).ready(init);

    let addresseeId = null;
    let addresseeQueryTimeout = null;

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

        addresseeQueryTimeout = setTimeout(loadAddressees, 500);

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