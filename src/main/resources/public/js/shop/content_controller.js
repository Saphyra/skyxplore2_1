(function ContentController(){
    window.contentController = new function(){
        this.displayElements = displayElements;
    }
    
    function displayElements(type){
        try{
            notificationService.showSuccess(type);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();