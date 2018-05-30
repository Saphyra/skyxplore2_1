(function PageController(){
    window.pageController = new function(){
        //TODO implement
    }
})();

/*$(document).ready(function(){
    pageController.refresh();
    init();
    
    function init(){
        try{
            document.getElementById("newcharactername").onkeydown = function(e){
                if(e.which == 13){
                    characterController.createCharacter();
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
});

(function PageController(){
    window.pageController = new function(){
        this.refresh = refresh;
        this.renameCharacter = renameCharacter
        this.setRenameCharacterContainerDisplayStatus = setRenameCharacterContainerDisplayStatus;
    }
    
    function refresh(){
        try{
            characterController.showCharacters();
            pageController.setRenameCharacterContainerDisplayStatus(false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function renameCharacter(characterId, characterName){
        try{
            document.getElementById("renamecharacterid").value = characterId;
            const inputField = document.getElementById("renamecharacterinput");
                inputField.value = characterName;
            pageController.setRenameCharacterContainerDisplayStatus(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function setRenameCharacterContainerDisplayStatus(status){
        try{
            document.getElementById("renamecharactercontainer").style.display = status ? "block" : "none";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();*/