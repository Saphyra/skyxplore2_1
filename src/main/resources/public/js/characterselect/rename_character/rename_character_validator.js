(function RenameCharacterValidator(){
    scriptLoader.loadScript("/js/common/validation_util.js");
    scriptLoader.loadScript("/js/account/validation_result.js");
    
    events.VALIDATE_NEW_CHARACTER_NAME = "validate_new_character_name";
    
    const INVALID_CHARACTERNAME = "#invalid-new-character-name";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_NEW_CHARACTER_NAME},
        validateCharacterName
    ));
    
    function validateCharacterName(){
        const characterName = getCharacterName();
        let characterNameValid = true;
        
        const validationResult = new ValidationResult([INVALID_CHARACTERNAME]);
        
        if(characterName.length < 3){
            validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "charactername-too-short");
            characterNameValid = false;
        }else if(characterName.length > 30){
            validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "charactername-too-long");
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
                        validationResult.createErrorFieldFor(INVALID_CHARACTERNAME, "charactername-already-exists");
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
                eventProcessor.processEvent(new Event(events.ALLOW_RENAME_CHARACTER));
            }else{
                eventProcessor.processEvent(new Event(events.BLOCK_RENAME_CHARACTER));
            }
        }
        
        function isValid(){
            return characterNameValid && characterName === getCharacterName();
        }
        
        function getCharacterName(){
            return $("#new-character-name").val();
        }
    }
})();