(function MailOperations(){
    events.MARK_AS_READ = "mark_as_read";
    events.MAILS_MARKED_AS_READ = "mails_marked_as_read";
    events.ARCHIVE_MAILS = "archive_mails";
    events.MAILS_ARCHIVED = "mails_archived";
    events.RESTORE_MAILS = "restore_mails";
    events.MAILS_RESTORED = "mails_restored";
    events.DELETE_MAILS = "delete_mails";
    events.MAILS_DELETED = "mails_deleted";

    window.Mode = {
        INCOMING: "incoming",
        ARCHIVED: "archived",
        SENT: "sent"
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MARK_AS_READ},
        function(event){markAsRead(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ARCHIVE_MAILS},
        function(event){archive(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.RESTORE_MAILS},
        function(event){restore(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DELETE_MAILS},
        function(event){deleteMails(event.getPayload().mailIds, event.getPayload().mode)}
    ));

    function markAsRead(mailIds){
        if(mailIds.length == 0){
            notificationService.showError(MessageCode.getMessage("SELECT_MAILS"));
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.MARK_MAILS_READ, mailIds);
            request.processValidResponse = function(){
                for(let mIndex in mailIds){
                    const mailId = mailIds[mIndex];
                    document.getElementById(generateIncomingMailId(mailId)).classList.remove("unread-mail");
                    incomingMailsController.mailReadMapping[mailId] = true;
                    incomingMailsController.setMarkButtonState(mailId, document.getElementById(generateMarkButtonId(mailId)));
                }

                eventProcessor.processEvent(new Event(events.MAILS_MARKED_AS_READ));
            }
        dao.sendRequestAsync(request);
    }

    function archive(mailIds){
        if(mailIds.length == 0){
            notificationService.showError(MessageCode.getMessage("SELECT_MAILS"));
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.ARCHIVE_MAILS, mailIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("MAILS_ARCHIVED"));
                for(let mIndex in mailIds){
                    document.getElementById("incoming-mail-list").removeChild(document.getElementById(generateIncomingMailId(mailIds[mIndex])));
                }
                eventProcessor.processEvent(new Event(events.MAILS_ARCHIVED));
            }
        dao.sendRequestAsync(request);
    }

    function restore(mailIds){
        if(mailIds.length == 0){
            notificationService.showError(MessageCode.getMessage("SELECT_MAILS"));
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.RESTORE_MAILS, mailIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("MAILS_RESTORED"));
                for(let mIndex in mailIds){
                    document.getElementById("archived-mail-list").removeChild(document.getElementById(generateArchivedMailId(mailIds[mIndex])));
                }
                eventProcessor.processEvent(new Event(events.MAILS_RESTORED));
            }
        dao.sendRequestAsync(request);
    }

    function deleteMails(mailIds, mode){
        if(mailIds.length == 0){
            notificationService.showError(MessageCode.getMessage("SELECT_MAILS"));
            return;
        }

        if(!confirm(MessageCode.getMessage("CONFIRM_DELETE_MAILS"))){
            return;
        }

        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_MAILS, mailIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("MAILS_DELETED"));
                for(let mIndex in mailIds){
                    document.getElementById(getContainerId(mode)).removeChild(document.getElementById(generateItemId(mode, mailIds[mIndex])));
                }
                eventProcessor.processEvent(new Event(events.MAILS_DELETED));
            }
        dao.sendRequestAsync(request);

        function getContainerId(mode){
            switch(mode){
                case Mode.INCOMING:
                    return "incoming-mail-list";
                break;
                case Mode.ARCHIVED:
                    return "archived-mail-list";
                break;
                case Mode.SENT:
                    return "sent-mail-list";
                break;
                default:
                    throwException("IllegalArgument", "Unknown mode: " + mode);
                break;
            }
        }

        function generateItemId(mode, mailId){
            switch(mode){
                case Mode.INCOMING:
                    generateIncomingMailId(mailId);
                break;
                case Mode.ARCHIVED:
                    return generateArchivedMailId(mailId);
                break;
                case Mode.SENT:
                    return generateSentMailId(mailId);
                break;
                default:
                    throwException("IllegalArgument", "Unknown mode: " + mode);
                break;
            }
        }
    }
})();