(function CreateCharacterValidator(){
    scriptLoader.loadScript("js/common/validation_util.js");
    scriptLoader.loadScript("js/account/validation_result.js");
    
    events.VALIDATE_CREATE_CHARACTER = "validate_create_character";
    
    const INVALID_CHARACTERNAME = "#invalid-create-chararacter-name";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_CREATE_CHARACTER},
        validateCharacterName
    ));
    
    function validateCharacterName(){
        const characterName = getCharacterName();
        let characterNameValid = true;
        
        const validationResult = new ValidationResult([INVALID_CHARACTERNAME]);
        
        if(characterName.length < 3){
            validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "CHARACTER_NAME_TOO_SHORT");
            characterNameValid = false;
        }else if(characterName.length > 30){
            validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "CHARACTER_NAME_TOO_LONG");
            characterNameValid = false;
        }else{
            const request = new Request(HttpMethod.POST, Mapping.CHARACTER_NAME_EXISTS, {value: characterName});
                request.isResponseOk = function(response){
                    return response.status == ResponseStatus.OK & (response.body === "true" || response.body === "false");
                }
                request.processValidResponse = function(response){
                    if(response.body === "false"){
                        characterNameValid = true;
                    }else{
                        characterNameValid = false;
                        validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "CHARACTER_NAME_ALREADY_EXISTS");
                    }
                    processValidationResult();
                }
            dao.sendRequestAsync(request);
            return;
        }
        
        processValidationResult();
        
        function processValidationResult(){
            validationResult.processResult();
            if(isValid()){
                eventProcessor.processEvent(new Event(events.ALLOW_CREATE_CHARACTER));
            }else{
                eventProcessor.processEvent(new Event(events.BLOCK_CREATE_CHARACTER));
            }
        }
        
        function isValid(){
            return characterNameValid && characterName === getCharacterName();
        }
        
        function getCharacterName(){
            return $("#create-character-name").val();
        }
    }
})();