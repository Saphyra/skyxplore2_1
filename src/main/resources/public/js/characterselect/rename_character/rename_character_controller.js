(function RenameCharacterController(){
    events.RENAME_CHARACTER_ATTEMPT = "rename_character_attempt";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.RENAME_CHARACTER_ATTEMPT},
        openRenamePage
    ));

    function openRenamePage(event){
        logService.log(event.getPayload(), "info", "Opening rename character page for character ");
    }
})();