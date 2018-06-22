(function MenuController(){
    window.menuController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        $(document).ready(function(){
            displayMenu();
        })
    }
    
    function displayMenu(){
        try{
            const container = document.getElementById("menu");
            const categories = dataDao.getCategories("shop");
            
            for(let cindex in categories){
                container.appendChild(createMenuElement(categories[cindex]));
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createMenuElement(item){
            try{
                const element = document.createElement("DIV");
                    element.classList.add("menuitem");
                    
                    const buttonElement = document.createElement("DIV");
                        buttonElement.classList.add("button");
                        buttonElement.innerHTML = item.name;
                        buttonElement.onclick = function(){contentController.displayElements(item.type, true)}
                element.appendChild(buttonElement);
                
                if(item.elements){
                    const childContainer = document.createElement("DIV");
                        childContainer.classList.add("menuchild");
                        childContainer.style.display = "none";
                        
                    for(let eindex in item.elements){
                        const childElement = createChildElement(item.elements[eindex]);
                        childContainer.appendChild(childElement);
                    }
                    element.appendChild(childContainer);
                    
                    const openMenuButton = document.createElement("BUTTON");
                        openMenuButton.innerHTML = "v";
                        openMenuButton.classList.add("openmenubutton");
                        openMenuButton.onclick = function(){
                            switch(childContainer.style.display){
                                case "none":
                                    childContainer.style.display = "block";
                                    openMenuButton.innerHTML = "^";
                                break;
                                case "block":
                                    childContainer.style.display = "none";
                                    openMenuButton.innerHTML = "v";
                                break;
                            }
                        }
                    element.appendChild(openMenuButton);
                }
                
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
            
            function createChildElement(item){
                try{
                    const button = document.createElement("DIV");
                        button.classList.add("button");
                        button.classList.add("childbutton");
                        button.innerHTML = item.name;
                        button.onclick = function(){contentController.displayElements(item.type, true)}
                    return button;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
        }
    }
    
})();