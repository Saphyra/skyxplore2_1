(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/common/dao/game_dao.js");
        
        this.createLobby = createLobby;
        
        $(document).ready(function(){
            $("label").on("click", function(e){e.stopPropagation()});
        });
    }
    
    function createLobby(gameMode){
        try{
            let data = null;
            if(gameMode == "clanwar"){
                data = $("#clanwarmode").val();
            }else if(gameMode == "teamfight"){
                data = $("#teamfightsize").val();
            }
            
            if(gameDao.createLobby({gameMode: gameMode, data: data})){
                //TODO redirect to lobby
                notificationService.showSuccess("Lobby created");
            }else{
                notificationService.showError("Hiba a kötelék létrehozása közben.");
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();