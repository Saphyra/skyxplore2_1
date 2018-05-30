(function PageController(){
    window.pageController = new function(){
        this.init = init;
        this.refresh = refresh;
        this.renameCharacter = renameCharacter;
        this.setRenameCharacterContainerDisplayStatus = setRenameCharacterContainerDisplayStatus;
        
        $(document).ready(function(){
            pageController.refresh();
            pageController.init();
        });
    }
    
    /*
    Adds event listener to newcharactername input field
    */
    function init(){
        try{
            document.getElementById("newcharactername").onkeydown = function(e){
                if(e.which == 13){
                    characterController.createCharacter();
                }
                characterController.validateNewCharacterName();
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Refreshes the page.
    */
    function refresh(){
        try{
            characterController.showCharacters();
            pageController.setRenameCharacterContainerDisplayStatus(false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Opens the "Rename Character" window with the given parameters.
    Parameters:
        - characterId: The id of the selected character.
        - characterName: The name of the selected character.
    Throws:
        - IllegalArgument if characerId / characterName is null or undefined.
    */
    function renameCharacter(characterId, characterName){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characerId must not be null or undefined");
            }
            if(characterName == null || characterName == undefined){
                throwException("IllegalArgument", "characterName must not be null or undefined");
            }
            document.getElementById("renamecharacterid").value = characterId;
            const inputField = document.getElementById("renamecharacterinput");
                inputField.value = characterName;
            pageController.setRenameCharacterContainerDisplayStatus(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Displays or hides the "Rename Character" window.
    Parameters:
        - status: the window should be shown.
    */
    function setRenameCharacterContainerDisplayStatus(status){
        try{
            document.getElementById("renamecharactercontainer").style.display = status ? "block" : "none";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();