(function BulkOperationsWithArchivedMails(){
    events.SELECT_ALL_ARCHIVED_MAILS = "select_all_archived_mails";
    events.PROCESS_BULK_OPERATION_WITH_ARCHIVED_MAILS = "process_bulk_operation_with_archived_mails";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SELECT_ALL_ARCHIVED_MAILS},
        function(){
            $("input[name='archived-mail-checkbox']").prop("checked", true);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.PROCESS_BULK_OPERATION_WITH_ARCHIVED_MAILS},
        function(){
            const selection = document.getElementById("action-with-archived-mails").value;
            const mailIds = getMailIds();

            switch(selection){
                case "none":
                    notificationService.showError(MessageCode.getMessage("SELECT_OPERATION"));
                break;
                case "restore":
                    eventProcessor.processEvent(new Event(events.RESTORE_MAILS, mailIds));
                break;
                case "delete":
                    eventProcessor.processEvent(new Event(events.DELETE_MAILS, {mailIds: mailIds, mode: Mode.ARCHIVED}));
                break;
                default:
                    throwException("UnsupportedOperation", "Not implemented: " + selection);
                break;
            }
        }
    ));

    function getMailIds(){
        const selected = $("input[name='archived-mail-checkbox']:checked");
        const result = [];
        for(let cindex = 0; cindex < selected.length; cindex++){
            result.push(selected[cindex].value);
        }
        return result;
    }
})();