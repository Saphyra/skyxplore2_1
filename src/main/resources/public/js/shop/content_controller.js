(function ContentController(){
    window.contentController = new function(){
        this.displayElements = displayElements;
    }
    
    /*
    Displays the items of the given type.
    Arguments:
        - type: the category
    Throws:
        - IllegalArgument exception if type is null or undefined.
    */
    function displayElements(type){
        try{
            const categories = dataDao.getCategoryEquipments(type);
            
            logService.log(categories);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();