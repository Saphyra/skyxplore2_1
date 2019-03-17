(function IncomingMailsController(){
    events.MARK_AS_READ = "mark_as_read";
    events.MAILS_MARKED_AS_READ = "mails_marked_as_read";
    events.ARCHIVE_MAILS = "archive_mails";

    let isActive = false;
    let mailReadMapping = {};

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.OPEN_WRITE_MAIL_WINDOW
                || eventType === events.OPEN_SENT_MAILS_TAB
        },
        function(){
            isActive = false;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MARK_AS_READ},
        function(event){markAsRead(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_INCOMING_MAILS_TAB},
        function(){
            isActive = true;
            loadIncomingMails();
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ARCHIVE_MAILS},
        function(event){archive(event.getPayload())}
    ));

    function markAsRead(mailIds){
        const request = new Request(HttpMethod.POST, Mapping.MARK_MAILS_READ, mailIds);
            request.processValidResponse = function(){
                for(let mIndex in mailIds){
                    const mailId = mailIds[mIndex];
                    document.getElementById(generateIncomingMailId(mailId)).classList.remove("unread-mail");
                    mailReadMapping[mailId] = true;
                    setMarkButtonState(mailId, document.getElementById(generateMarkButtonId(mailId)));
                }

                eventProcessor.processEvent(new Event(events.MAILS_MARKED_AS_READ));
            }
        dao.sendRequestAsync(request);
    }

    function archive(mailIds){
        const request = new Request(HttpMethod.POST, Mapping.ARCHIVE_MAILS, mailIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("MAILS_ARCHIVED"));
                for(let mIndex in mailIds){
                    document.getElementById("incoming-mail-list").removeChild(document.getElementById(generateIncomingMailId(mailIds[mIndex])));
                }
            }
        dao.sendRequestAsync(request);
    }

    function loadIncomingMails(){
        const container = document.getElementById("incoming-mail-list");
            container.innerHTML = "";

        const request = new Request(HttpMethod.GET, Mapping.GET_INCOMING_MAILS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(incomingMails){
                incomingMails.sort(function(a, b){
                   return b.sendTime - a.sendTime;
                });

                if(incomingMails.length == 0){
                    $("#no-incoming-mail").show();
                    return;
                }else{
                    $("#no-incoming-mail").hide();
                    for(let mIndex in incomingMails){
                        const mail = incomingMails[mIndex];
                        mailReadMapping[mail.mailId] = mail.read;
                        container.appendChild(createMailItem(mail));
                    }
                }
            }
        dao.sendRequestAsync(request);
    }

    function createMailItem(mail){
        const container = document.createElement("DIV");
            container.classList.add("mail-item");
            container.id = generateIncomingMailId(mail.mailId);

            if(!mail.read){
                container.classList.add("unread-mail");
            }

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

                const replyButton = document.createElement("BUTTON");
                    replyButton.innerHTML = Localization.getAdditionalContent("reply");
                    replyButton.onclick = function(){
                        document.getElementById("subject").value = "Re: " + mail.subject;
                        writeMailController.setAddressee({characterId: mail.from, characterName: mail.fromName});
                        eventProcessor.processEvent(new Event(events.OPEN_WRITE_MAIL_WINDOW));
                    }
            mailBody.appendChild(replyButton);
        container.appendChild(mailBody);

            mailHeader.onclick = function(){
                $(mailBody).fadeToggle();
                if(!mailReadMapping[mail.mailId]){
                    eventProcessor.processEvent(new Event(events.MARK_AS_READ, [mail.mailId]));
                }
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
                            checkbox.name = "incoming-mail-checkbox-selected";
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

                        const archiveButton = document.createElement("BUTTON");
                            archiveButton.innerHTML = Localization.getAdditionalContent("archive");
                            archiveButton.onclick = function(e){
                                e.stopPropagation();
                                eventProcessor.processEvent(new Event(events.ARCHIVE_MAILS, [mail.mailId]));
                            }
                    buttonCell.appendChild(archiveButton);

                        const markButton = document.createElement("BUTTON");
                            markButton.id = generateMarkButtonId(mail.mailId);
                            setMarkButtonState(mail.mailId, markButton);
                    buttonCell.appendChild(markButton);
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

    function setMarkButtonState(mailId, markButton){
        if(mailReadMapping[mailId]){
            markButton.innerHTML = Localization.getAdditionalContent("mark-as-unread");
            markButton.onclick = function(e){
                e.stopPropagation();
                eventProcessor.processEvent(new Event(events.MARK_AS_UNREAD, [mailId]));
            }
        }else{
            markButton.innerHTML = Localization.getAdditionalContent("mark-as-read");
            markButton.onclick = function(e){
                e.stopPropagation();
                eventProcessor.processEvent(new Event(events.MARK_AS_READ, [mailId]));
            }
        }
    }

    function generateIncomingMailId(mailId){
        return "incoming-mail-" + mailId;
    }

    function generateMarkButtonId(mailId){
        return "mark-button-" + mailId;
    }
})();