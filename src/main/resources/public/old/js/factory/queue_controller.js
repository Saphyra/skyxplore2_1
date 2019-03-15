(function QueueController(){
    window.queueController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        this.queue = [];
        
        this.addToQueue = addToQueue;
        this.displayQueue = displayQueue;
        this.loadQueue = loadQueue;
    }
    
    /*
    Adds the selected element to the factory queue.
    Arguments:
        - elementId: the id of the element to add.
        - amount: the amount
    Throws:
        - UnknownServerError exception if request fails
    */
    function addToQueue(elementId, amount){
        try{
            const result = factoryDao.addToQueue(elementId, amount);
            if(result.status == ResponseStatus.OK){
                notificationService.showSuccess("Megrendelés elküldve.");
            }else{
                throwException("UnknownServerError", result.toString());
            }
            pageController.refresh(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();