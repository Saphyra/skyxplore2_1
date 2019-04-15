(function MailOperations(){
    events.MARK_AS_READ = "mark_as_read";
    events.MAILS_MARKED_AS_READ = "mails_marked_as_read";
    events.ARCHIVE_MAILS = "archive_mails";
    events.MAILS_ARCHIVED = "mails_archived";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MARK_AS_READ},
        function(event){markAsRead(event.getPayload())}
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
                eventProcessor.processEvent(new Event(events.MAILS_ARCHIVED));
            }
        dao.sendRequestAsync(request);
    }
})();