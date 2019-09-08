(function BulkOperationsWithSentMails(){
    events.SELECT_ALL_SENT_MAILS = "select_all_sent_mails";
    events.PROCESS_BULK_OPERATION_WITH_SENT_MAILS = "process_bulk_operation_with_sent_mails";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SELECT_ALL_SENT_MAILS},
        function(){
            $("input[name='sent-mail-checkbox']").prop("checked", true);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.PROCESS_BULK_OPERATION_WITH_SENT_MAILS},
        function(){
            const selection = document.getElementById("action-with-sent-mails").value;
            const mailIds = getMailIds();

            switch(selection){
                case "none":
                    notificationService.showError(Localization.getAdditionalContent("select-operation"));
                break;
                case "delete":
                    eventProcessor.processEvent(new Event(events.DELETE_MAILS, {mailIds: mailIds, mode: Mode.SENT}));
                break;
                default:
                    throwException("UnsupportedOperation", "Not implemented: " + selection);
                break;
            }
        }
    ));

    function getMailIds(){
        const selected = $("input[name='sent-mail-checkbox']:checked");
        const result = [];
        for(let cindex = 0; cindex < selected.length; cindex++){
            result.push(selected[cindex].value);
        }
        return result;
    }
})();