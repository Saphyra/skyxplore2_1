(function MaterialsController(){
    window.materialsController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        
        this.displayMaterials = displayMaterials;
    }
    
    /*
    Queries and displays the materials of the storage.
    */
    function displayMaterials(){
        try{
            const container = document.getElementById("materials");
                container.innerHTML = "";
                
            const materials = orderMaterials(factoryDao.getMaterials());
            
            for(let mindex in materials){
                container.appendChild(createMaterialElement(materials[mindex]));
            }
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createMaterialElement(material){
            try{
                const container = document.createElement("DIV");
                    container.title = material.description;
                    container.classList.add("borderbottomridge");
                    container.classList.add("border1px");
                    container.classList.add("bordercoloraaa");
                    container.classList.add("margintop0_5rem");
                    
                    const nameContainer = document.createElement("SPAN");
                        nameContainer.innerHTML = material.name + ": ";
                container.appendChild(nameContainer);
                    
                    const amountContainer = document.createElement("DIV");
                        amountContainer.classList.add("inlineblock");
                        amountContainer.style.minWidth = "3rem";
                        amountContainer.innerHTML = material.amount;
                container.appendChild(amountContainer);
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
        
        function orderMaterials(materials){
            try{
                const result = {};
                const elementList = [];
                
                for(let mindex in materials){
                    elementList.push(materials[mindex]);
                }
                
                elementList.sort(function(a, b){
                    return a.name.localeCompare(b.name);
                })
                
                for(let eindex in elementList){
                    const element = elementList[eindex];
                    result[element.materialId] = element;
                }
                
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return {};
            }
        }
    }
})();