(function SelectCharacter(){
    events.SELECT_CHARACTER = "select_character";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SELECT_CHARACTER},
        selectCharacter
    ));
    
    function selectCharacter(event){
        logService.log(event.getPayload(), "info", "Character selected with id ");
    }
})();