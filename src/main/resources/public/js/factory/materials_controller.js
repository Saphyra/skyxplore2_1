(function MaterialsController(){
    window.materialsController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        
        this.displayMaterials = displayMaterials;
    }
    
    function displayMaterials(){
        try{
            const container = document.getElementById("materials");
                container.innerHTML = "";
                
            const materials = orderMaterials(factoryDao.getMaterials());
            
            logService.log(materials);
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
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