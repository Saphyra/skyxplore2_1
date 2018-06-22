(function MenuController(){
    window.menuController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.categories = null;
        
        $(document).ready(function(){
            loadCategories();
            createCategoryElements();
        });
    }
    
    function loadCategories(){
        try{
            menuController.categories = dataDao.getCategories("factory");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function createCategoryElements(){
        try{
            const container = document.getElementById("menu");
            for(let cindex in  menuController.categories){
                container.appendChild(createMenuElement(menuController.categories[cindex]));
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createMenuElement(category){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("relative");
                    
                    const selectButton =  document.createElement("BUTTON");
                        selectButton.innerHTML = category.name;
                        selectButton.onclick = function(){
                            contentController.displayElementsOfCategory(category.type);
                            $("#menu").fadeToggle();
                        };
                container.appendChild(selectButton);
                
                    const openButtonPlaceHolder = document.createElement("DIV");
                        openButtonPlaceHolder.classList.add("inlineblock");
                        openButtonPlaceHolder.classList.add("width2rem");
                container.appendChild(openButtonPlaceHolder);
                    
                    if(category.elements){
                        const openButton = document.createElement("BUTTON");
                            openButton.innerHTML = ">";
                    openButtonPlaceHolder.appendChild(openButton);
                        
                        const subContainer = document.createElement("DIV");
                            subContainer.classList.add("relative");
                            subContainer.classList.add("left65percent");
                            subContainer.classList.add("textalignleft");
                            subContainer.classList.add("width2rem");
                            subContainer.style.display = "none";
                            for(let eindex in category.elements){
                                subContainer.appendChild(createMenuElement(category.elements[eindex]));
                            }
                        container.appendChild(subContainer);
                        
                        openButton.onclick = function(){
                            switch(subContainer.style.display){
                                case "none":
                                    openButton.innerHTML = "<";
                                    $(subContainer).fadeIn(400, function(){subContainer.style.display = "block";});
                                break;
                                case "block":
                                    openButton.innerHTML = ">";
                                    $(subContainer).fadeOut(400, function(){subContainer.style.display = "none";});
                                break;
                            }
                        }
                    }
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();