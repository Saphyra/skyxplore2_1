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
                    const materialsContainer = document.createElement("DIV");
                        if(!fillMaterialsContainer(materialsContainer, element, 1)){
                            isBuildable = false;
                        }
                container.appendChild(materialsContainer);
                
                    const costContainer = document.createElement("DIV");
                        costContainer.classList.add("fontsize1_25rem");
                        costContainer.innerHTML = "Pénz: ";
                        const cost = document.createElement("SPAN");
                            cost.innerHTML = element.buildprice;
                            if(element.buildprice > pageController.money){
                                isBuildable = false;
                            }
                    costContainer.appendChild(cost);
                        const current = document.createElement("SPAN");
                            current.innerHTML = " / " + pageController.money;
                    costContainer.appendChild(current);
                    costContainer.style.color = element.buildprice > pageController.money ? "red" : "lightgreen";
                container.appendChild(costContainer);
                
                    const constructionTimeContainer = document.createElement("DIV");
                        constructionTimeContainer.classList.add("fontsize1_25rem");
                        constructionTimeContainer.classList.add("margin0_25rem");
                        constructionTimeContainer.innerHTML = "Gyártási idő: "
                        const constructionTime = document.createElement("SPAN");
                            constructionTime.innerHTML = translator.convertTimeStamp(element.constructiontime).toString();
                    constructionTimeContainer.appendChild(constructionTime);
                container.appendChild(constructionTimeContainer);
                
                    const amountContainer = document.createElement("LABEL");
                        amountContainer.classList.add("fontsize1_25rem");
                        amountContainer.appendChild(document.createTextNode("Mennyiség: "));
                        const amountInput = document.createElement("INPUT");
                            amountInput.classList.add("width4rem");
                            amountInput.type = "number";
                            amountInput.min = 1;
                            amountInput.value = 1;
                            amountInput.placeholder = "Mennyiség";
                    amountContainer.appendChild(amountInput);
                container.appendChild(amountContainer);
                
                    const buildButton = document.createElement("BUTTON");
                        buildButton.innerHTML = "Gyártás indítása";
                        buildButton.onclick = function(){
                            queueController.addToQueue(element.id, Number(amountInput.value));
                        }
                container.appendChild(buildButton);
                
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