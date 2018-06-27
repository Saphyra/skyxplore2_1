(function QueueController(){
    window.queueController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        
        this.addToQueue = addToQueue;
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
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();