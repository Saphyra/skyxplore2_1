(function BulkOperationsWithIncomingMails(){
    events.SELECT_ALL_INCOMING_MAILS = "select_all_incoming_mails";
    events.PROCESS_BULK_OPERATION_WITH_INCOMING_MAILS = "process_bulk_operation_with_incoming_mails";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SELECT_ALL_INCOMING_MAILS},
        function(){
            $("input[name='incoming-mail-checkbox']").prop("checked", true);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.PROCESS_BULK_OPERATION_WITH_INCOMING_MAILS},
        function(){
            const selection = document.getElementById("action-with-incoming-mails").value;
            const mailIds = getMailIds();

            switch(selection){
                case "none":
                    notificationService.showError(MessageCode.getMessage("SELECT_OPERATION"));
                break;
                case "archive":
                    eventProcessor.processEvent(new Event(events.ARCHIVE_MAILS, mailIds));
                break;
                case "delete":
                    eventProcessor.processEvent(new Event(events.DELETE_MAILS, {mailIds: mailIds, mode: Mode.INCOMING}));
                break;
                default:
                    throwException("UnsupportedOperation", "Not implemented: " + selection);
                break;
            }
        }
    ));

    function getMailIds(){
        const selected = $("input[name='incoming-mail-checkbox']:checked");
        const result = [];
        for(let cindex = 0; cindex < selected.length; cindex++){
            result.push(selected[cindex].value);
        }
        return result;
    }
})();