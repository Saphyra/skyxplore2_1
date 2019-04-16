(function ArchivedMailsController(){
    let isActive = false;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.OPEN_SENT_MAILS_TAB
                || eventType === events.OPEN_INCOMING_MAILS_TAB
        },
        function(){
            isActive = false;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_ARCHIVED_MAILS_TAB},
        function(){
            isActive = true;
            loadArchivedMails();
        }
    ));

    function loadArchivedMails(){
        const container = document.getElementById("archived-mail-list");
            container.innerHTML = "";

        const request = new Request(HttpMethod.GET, Mapping.GET_ARCHIVED_MAILS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(archivedMails){
                archivedMails.sort(function(a, b){
                   return b.sendTime - a.sendTime;
                });

                if(archivedMails.length == 0){
                    $("#no-archived-mail").show();
                    return;
                }else{
                    $("#no-archived-mail").hide();
                    for(let mIndex in archivedMails){
                        const mail = archivedMails[mIndex];
                        container.appendChild(createMailItem(mail));
                    }
                }
            }
        dao.sendRequestAsync(request);
    }

    function createMailItem(mail){
        const container = document.createElement("DIV");
            container.classList.add("mail-item");
            container.id = generateArchivedMailId(mail.mailId);

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
                            checkbox.name = "archived-mail-checkbox-selected";
                            checkbox.value = mail.mailId;
                            checkbox.onclick = function(e){e.stopPropagation()};
                    checkboxCell.appendChild(checkbox);
                row1.appendChild(checkboxCell);
                
                    const fromCell = document.createElement("TD");
                        fromCell.appendChild(createSpan(Localization.getAdditionalContent("sender")));
                        fromCell.appendChild(createSpan(": "));
                        fromCell.appendChild(createSpan(mail.fromName));
                row1.appendChild(fromCell);
                
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
                                eventProcessor.processEvent(new Event(events.DELETE_MAILS, [mail.mailId]));
                            }
                    buttonCell.appendChild(deleteButton);
                    
                        const restoreButton = document.createElement("BUTTON");
                            restoreButton.innerHTML = Localization.getAdditionalContent("restore");
                            restoreButton.onclick = function(e){
                                e.stopPropagation();
                                eventProcessor.processEvent(new Event(events.RESTORE_MAILS, [mail.mailId]));
                            }
                    buttonCell.appendChild(restoreButton);
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