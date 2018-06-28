(function QueueController(){
    window.queueController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        this.queue = {}
        
        this.addToQueue = addToQueue;
        this.displayQueue = displayQueue;
        this.loadQueue = loadQueue;
    }
    
    /*
    Adds the selected element to the factory queue.
    Arguments:
        - elementId: the id of the element to add.
        - amount: the amount
    */
    function addToQueue(elementId, amount){
        try{
            const result = factoryDao.addToQueue(sessionStorage.characterId, elementId, amount);
            if(result.status == 200){
                notificationService.showSuccess("Megrendelés elküldve.");
            }else{
                throwExveption("UnknownServerError", result.toString());
            }
            pageController.refresh(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function displayQueue(){
        try{
            logService.log(queueController.queue);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function loadQueue(){
        try{
            const result = factoryDao.getQueue(sessionStorage.characterId);
            
            queueController.queue = result;
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();