(function ContentController(){
    window.contentController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.selectedCategory = "all";
        
        this.categoryCache = new CategoryCache();
        
        this.displayElementsOfCategory = displayElementsOfCategory;
    }
    
    function displayElementsOfCategory(category){
        try{
            category = category || contentController.selectedCategory;
            contentController.selectedCategory = category;
            
            const elements = contentController.categoryCache.getElementsOfCategory(category);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function CategoryCache(){
        const storage = {};
        
        this.getElementsOfCategory = function(categoryId){
            if(!storage[categoryId]){
                const elements = dataDao.getCategoryEquipments(categoryId);
                storage[categoryId] = elements;
            }
            
            return storage[categoryId];
        }
    }
})();