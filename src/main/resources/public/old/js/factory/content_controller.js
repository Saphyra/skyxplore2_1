(function ContentController(){
    window.contentController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        scriptLoader.loadScript("js/common/translator/title_service.js");
        
        this.selectedCategory = "all";
        
        this.categoryCache = new CategoryCache();
        
        this.displayElementsOfCategory = displayElementsOfCategory;
    }

    /*
    Displays the elements of the selected category.
    */
    function displayElementsOfCategory(category){
        function createElement(element){
            try{
                
                buildButton.disabled = !isBuildable;
                
                $(amountInput).on("change keyup", function(){
                    materialsContainer.innerHTML = "";
                    isBuildable = true;
                    if(!fillMaterialsContainer(materialsContainer, element, amountInput.value)){
                        isBuildable = false;
                    }
                    
                    constructionTime.innerHTML = translator.convertTimeStamp(element.constructiontime * amountInput.value).toString();
                    
                    cost.innerHTML = element.buildprice * amountInput.value;
                    costContainer.style.color = element.buildprice * amountInput.value > pageController.money ? "red" : "lightgreen";
                    
                    if(element.buildprice * amountInput.value > pageController.money){
                        isBuildable = false;
                    }
                    
                    buildButton.disabled = !isBuildable;
                });
                
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
    }
})();