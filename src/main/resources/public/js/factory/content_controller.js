(function ContentController(){
    window.contentController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        scriptLoader.loadScript("js/common/translator/title_service.js");
        
        this.selectedCategory = "all";
        
        this.categoryCache = new CategoryCache();
        
        this.displayElementsOfCategory = displayElementsOfCategory;
    }
    
    function displayElementsOfCategory(category){
        try{
            category = category || contentController.selectedCategory;
            contentController.selectedCategory = category;
            
            const container = document.getElementById("content");
                container.innerHTML = "";
            
            const elements = orderElements(contentController.categoryCache.getElementsOfCategory(category));
            for(let eindex in elements){
                if(elements[eindex].materials){
                    container.appendChild(createElement(elements[eindex]));
                }
            }
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createElement(element){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("contentelement");
                    container.title = titleService.getTitleForOverview(element.id);
                    
                    const nameContainer = document.createElement("DIV");
                        nameContainer.classList.add("contentelementtitle");
                        nameContainer.innerHTML = element.name;
                container.appendChild(nameContainer);
                
                    const materialsData = orderMaterials(getMaterialsData(element.materials, element.id));
                    const materialsContainer = document.createElement("DIV");
                    for(let mindex in materialsData){
                        const materialData = materialsData[mindex].data;
                        const amount = materialsData[mindex].amount;
                        const storage = materialsController.materials[materialData.id].amount;
                        
                        const materialElement = createMaterialElement(amount > storage ? "red" : "green");
                            materialElement.title = titleService.getTitleForOverview(materialData.id);
                            materialElement.innerHTML = materialData.name + ": " + amount + " / " + storage;
                        materialsContainer.appendChild(materialElement);
                    }
                container.appendChild(materialsContainer);
                
                    const constructionTimeContainer = document.createElement("DIV");
                        constructionTimeContainer.classList.add("fontsize1_25rem");
                        constructionTimeContainer.classList.add("margin0_25rem");
                        constructionTimeContainer.innerHTML = "Gyártási idő: " + translator.convertTimeStamp(element.constructiontime).toString();
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
                        buildButton.classList.add("fontsize1_25rem");
                        buildButton.innerHTML = "Gyártás indítása";
                container.appendChild(buildButton);
                
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
            
            function createMaterialElement(color){
                const materialElement = document.createElement("DIV");
                    materialElement.classList.add("borderridge");
                    materialElement.classList.add("border1px");
                    materialElement.classList.add("bordercoloraaa");
                    materialElement.classList.add("inlineblock");
                    materialElement.classList.add("marginright0_5rem");
                    materialElement.classList.add("margintop0_25rem");
                    materialElement.classList.add("marginbottom0_25rem");
                    materialElement.classList.add("padding0_25rem");
                    materialElement.style.color = color;
                return materialElement;
            }
            
            function getMaterialsData(materials, id){
                try{
                    const result = {};
                    for(let materialId in materials){
                        const amount = materials[materialId];
                        result[materialId] = {
                            data: cache.get(materialId),
                            amount: amount
                        };
                    }
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(materials, "error", message);
                    return {};
                }
            }
            
            function orderMaterials(materials){
                try{
                    const result = {};
                    const list = [];
                    
                    for(let mindex in materials){
                        list.push(materials[mindex]);
                    }
                    
                    list.sort(function(a, b){return a.data.name.localeCompare(b.data.name);});
                    
                    for(let lindex in list){
                        const element = list[lindex];
                        result[element.data.id] = element;
                    }
                    
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(materials, "error", message);
                    return {};
                }
            }
        }
        
        function orderElements(elements){
            try{
                const result = {};
                const list = [];
                
                for(let eindex in elements){
                    list.push(elements[eindex]);
                }
                
                list.sort(function(a, b){
                    const aSlot = translator.translateSlot(a.slot);
                    const bSlot = translator.translateSlot(b.slot);
                    
                    if(aSlot === bSlot){
                        return a.name.localeCompare(b.name);
                    }else{
                        return aSlot.localeCompare(bSlot);
                    }
                });
                
                for(let lindex in list){
                    const element = list[lindex];
                    result[element.id] = element;
                }
                
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return {};
            }
        }
    }
    
    function CategoryCache(){
        const storage = {};
        
        this.getElementsOfCategory = function(categoryId){
            try{
                if(!storage[categoryId]){
                    const elements = dataDao.getCategoryEquipments(categoryId);
                    cache.addAll(elements);
                    storage[categoryId] = elements;
                }
                
                return storage[categoryId];
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return {};
            }
        }
    }
})();