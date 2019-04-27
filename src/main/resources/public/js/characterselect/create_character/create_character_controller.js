(function CreateCharacterController(){
    scriptLoader.loadScript("js/characterselect/create_character/create_character_validator.js");
    scriptLoader.loadScript("js/characterselect/create_character/create_character_service.js");
    
    events.VALIDATE_CREATE_CHARACTER_ATTEMPT = "validate_create_character_attempt";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_CREATE_CHARACTER_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_CREATE_CHARACTER));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, getValidationTimeout());
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_CREATE_CHARACTER));
        }
    }
    
    $(document).ready(function(){
        addEventListeners();
        
        function addEventListeners(){
            $("#create-character-name").on("keyup", function(e){
                if(e.which == 13){
                    eventProcessor.processEvent(new Event(events.CREATE_CHARACTER_ATTEMPT));
                }else{
                    eventProcessor.processEvent(new Event(events.VALIDATE_CREATE_CHARACTER_ATTEMPT));
                }
            });
            $("#create-character-name").on("focusin", function(){
                eventProcessor.processEvent(new Event(events.VALIDATE_CREATE_CHARACTER_ATTEMPT));
            });
        }
    });
})();