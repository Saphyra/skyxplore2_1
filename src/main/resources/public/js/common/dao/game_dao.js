(function GameDao(){
    window.gameDao = new function(){
        this.createLobby = createLobby;
    }
    
    /*
    Sends a create lobby request.
    Arguments:
        - data: object that hold the gamemode and the optional details.
    Returns:
        - true, if the new game created successfully.
        - false otherwise.
    Throws:
        - IllegalArgument exception if data is null or undefined.
        - IllegalArgument exception if data.gameMode is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function createLobby(data){
        try{
            if(data == null || data == undefined){
                throwException("IllegalArgument", "data must not be null or undefined.");
            }
            if(data.gameMode == null || data.gameMode == undefined){
                throwException("IllegalArgument", "data.gameMode must not be null or undefined.");
            }
            const path = "game/createlobby";
            const response = dao.sendRequest(dao.POST, path, data);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();