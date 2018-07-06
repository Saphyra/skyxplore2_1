(function ContentController(){
    window.contentController = new function(){
        scriptLoader.loadScript("js/common/translator/title_service.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        
        this.equipments = {};
        this.filter = null;
        
        this.displayElements = displayElements;
    }
    
    /*
    Displays the items of the given type.
    Arguments:
        - type: the category
        - needReload: Queries the type from server if true. Uses stored values if false.
    Throws:
        - IllegalArgument exception if type is null or undefined.
    */
    function displayElements(type, needReload){
        try{
            const container = document.getElementById("content");
                container.innerHTML = "";
                
            if(needReload){
                contentController.equipments = dataDao.getCategoryEquipments(type);
                cache.addAll(contentController.equipments);
                contentController.filter = type;
            }
            
            const equipments = orderCategories(contentController.equipments);
            
            for(let id in equipments){
                //Filtering elements queried by "all" but has no buyprice
                if(equipments[id].buyprice != null && equipments[id].buyprice != undefined){
                    container.appendChild(createElement(equipments[id]));
                }
            }
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }

        /*
        Order by: Slot and name
        */
        function orderCategories(equipments){
            try{
                const list = [];
                for(let cindex in equipments){
                    list.push({
                        id: cindex,
                        data: equipments[cindex]
                    });
                }
                
                list.sort(function(a, b){
                    const aSlot = translator.translateSlot(a.data.slot);
                    const bSlot = translator.translateSlot(b.data.slot);
                    
                    if(aSlot === bSlot){
                        return a.data.name.localeCompare(b.data.name);
                    }else{
                        return aSlot.localeCompare(bSlot);
                    }
                });
                
                const result = {};
                
                for(let lindex in list){
                    const listElement = list[lindex];
                    result[listElement.id] = listElement.data;
                }
                
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function createElement(data){
            try{
                const element = document.createElement("DIV");
                    element.classList.add("element");
                    element.title = titleService.getTitleForOverview(data.id);
                    
                    const titleContainer = document.createElement("DIV");
                        titleContainer.classList.add("elementtitlecontainer");
                        
                        const elementTitle = document.createElement("DIV");
                            elementTitle.classList.add("elementtitle");
                            elementTitle.innerHTML = data.name;
                    titleContainer.appendChild(elementTitle);
                    
                        const elementDescription = document.createElement("DIV");
                            elementDescription.classList.add("elementdesctription");
                            elementDescription.innerHTML = data.description;
                    titleContainer.appendChild(elementDescription);
                
                element.appendChild(titleContainer);
                    
                    const contentContainer = document.createElement("DIV");
                        contentContainer.classList.add("fontsize1_5rem");
                        contentContainer.classList.add("padding0_25rem");
                    
                        const priceContainer = document.createElement("DIV");
                            priceContainer.innerHTML = "Ár: " + data.buyprice;
                    contentContainer.appendChild(priceContainer);
                    
                        const buyButton = document.createElement("BUTTON");
                            buyButton.innerHTML = "Kosárba";
                            buyButton.classList.add("fontsize1_25rem");
                            buyButton.onclick = function(){basketController.addElement(data.id)};
                            
                            if(pageController.getMoney() - basketController.getBasketCost() - data.buyprice < 0){
                                buyButton.disabled = true;
                            }
                            
                    contentContainer.appendChild(buyButton);
                    
                element.appendChild(contentContainer);
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();