(function SentMailsController(){
    scriptLoader.loadScript("js/common/localization/date_time_formatter.js");

    let isActive = false;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_WRITE_MAIL_WINDOW},
        function(){
            isActive = false;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MAIL_SENT},
        function(){
            if(isActive){
                loadSentMails();
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_SENT_MAILS_TAB},
        function(){
            isActive = true;
            loadSentMails();
        }
    ));

    function loadSentMails(){
        const request = new Request(HttpMethod.GET, Mapping.GET_SENT_MAILS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(sentMails){
                sentMails.sort(function(a, b){
                   return b.sendTime - a.sendTime;
                });

                const container = document.getElementById("sent-mail-list");
                    container.innerHTML = "";

                if(sentMails.length == 0){
                    $("#no-sent-mail").show();
                    return;
                }else{
                    $("#no-sent-mail").hide();
                    for(let mIndex in sentMails){
                        container.appendChild(createMailItem(sentMails[mIndex]));
                    }
                }
            }
        dao.sendRequestAsync(request);
    }

    function createMailItem(mail){
        const container = document.createElement("DIV");
            container.id = mail.mailId;
            container.classList.add("mail-item");

            const mailHeader = document.createElement("DIV");
                mailHeader.classList.add("mail-header");
                mailHeader.appendChild(createMailHeaderTable(mail));
        container.appendChild(mailHeader);

            const mailBody = document.createElement("DIV");
                mailBody.classList.add("mail-body");

                const message = document.createElement("TEXTAREA");
                    message.disabled = true;
                    message.value = mail.message;
            mailBody.appendChild(message);
        container.appendChild(mailBody);

        mailHeader.onclick = function(){
            $(mailBody).fadeToggle();
        }

        return container;

        function createMailHeaderTable(mail){
            const table = document.createElement("TABLE");
                const row1 = document.createElement("TR");
                    const checkboxCell = document.createElement("TD");
                        checkboxCell.classList.add("checkbox-cell");
                        checkboxCell.onclick = function(e){e.stopPropagation()};
                        checkboxCell.rowSpan = 2;

                        const checkbox = document.createElement("INPUT");
                            checkbox.type = "checkbox";
                            checkbox.name = "checkbox-selected";
                            checkbox.value = mail.mailId;
                            checkbox.onclick = function(e){e.stopPropagation()};
                    checkboxCell.appendChild(checkbox);
                row1.appendChild(checkboxCell);

                    const addresseeCell = document.createElement("TD");
                        addresseeCell.appendChild(createSpan(Localization.getAdditionalContent("addressee")));
                        addresseeCell.appendChild(createSpan(": "));
                        addresseeCell.appendChild(createSpan(mail.toName));
                row1.appendChild(addresseeCell)

                    const sendTimeCell = document.createElement("TD");
                        sendTimeCell.classList.add("send-time-cell");
                        sendTimeCell.innerHTML = dateTimeFormatter.formatEpoch(mail.sendTime);
                row1.appendChild(sendTimeCell);

                    const buttonCell = document.createElement("TD");
                        buttonCell.rowSpan = 2;
                        buttonCell.classList.add("button-cell");

                        const deleteButton = document.createElement("BUTTON");
                            deleteButton.innerHTML = Localization.getAdditionalContent("delete");
                            deleteButton.onclick = function(e){
                                e.stopPropagation();
                                if(confirm(MessageCode.getMessage("CONFIRM_DELETE_MAILS"))){
                                    eventProcessor.processEvent(new Event(events.DELETE_MAILS, [mail.mailId]));
                                }
                            }
                    buttonCell.appendChild(deleteButton);
                row1.appendChild(buttonCell);
            table.appendChild(row1);

                const row2 = document.createElement("TR");
                    const subjectCell = document.createElement("TD");
                        subjectCell.colSpan = 2;
                        subjectCell.appendChild(createSpan(Localization.getAdditionalContent("subject")));
                        subjectCell.appendChild(createSpan(": "));
                        subjectCell.appendChild(createSpan(mail.subject));
                row2.appendChild(subjectCell);
            table.appendChild(row2);

            return table;
        }
    }
})();