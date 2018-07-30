(function PageController(){
    window.pageController = new function(){
        this.createGame = createGame;
        
        $(document).ready(function(){
            $("label").on("click", function(e){e.stopPropagation()});
        });
    }
    
    function createGame(gameMode){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();