(function MaterialsController(){
    scriptLoader.loadScript("/js/common/localization/items.js");

    events.LOAD_MATERIALS = "load_materials";
    events.MATERIALS_LOADED = "materials_loaded";

    let materials = [];

    window.materialsController = new function(){
        this.getMaterialAmount = getMaterialAmount;
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_MATERIALS
                || eventType === events.ADDED_TO_QUEUE
                || eventType === events.PRODUCT_FINISHED
        },
        loadMaterials
    ));

    function getMaterialAmount(materialId){
        for(let mIndex in materials){
            const material = materials[mIndex];

            if(material.getId() == materialId){
                return material.getAmount();
            }
        }

        return 0;
    }

    function loadMaterials(){
        materials = [];
        $("#no-materials").show();
        document.getElementById("materials").innerHTML = "";

        const request = new Request(HttpMethod.GET, Mapping.GET_MATERIALS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(payload){
                for(let materialId in payload){
                    displayMaterial(materialId, payload[materialId]);
                }
                eventProcessor.processEvent(new Event(events.MATERIALS_LOADED));
            }

        dao.sendRequestAsync(request);
    }

    function displayMaterial(materialId, amount){
        $("#no-materials").hide();

        const material = Items.getItem(materialId);
        const container = document.createElement("DIV");
            container.title = material.description;
            container.classList.add("material-element");

            const nameContainer = document.createElement("SPAN");
                nameContainer.innerHTML = material.name;
        container.appendChild(nameContainer);

            const delimiter = document.createElement("SPAN");
                delimiter.innerHTML = ": ";
        container.appendChild(delimiter);

            const amountContainer = document.createElement("SPAN");
                amountContainer.innerHTML = amount;
                amountContainer.classList.add("material-element-amount");
        container.appendChild(amountContainer);

        const nextMaterialIndex = findNextMaterialAlphabetically(material.name);
        const nextMaterial = materials[nextMaterialIndex] || null;
        document.getElementById("materials").insertBefore(container, nextMaterial ? nextMaterial.getContainer() : null);

        const materialObj = new Material(materialId, amount, container, amountContainer);
        materials.splice(nextMaterialIndex, 0, materialObj);

        function findNextMaterialAlphabetically(materialName){
            let result = 0;
            for(result; result < materials.length; result++){
                const nextMaterialName = Items.getItem(materials[result].getId()).name;
                if(materialName.localeCompare(nextMaterialName) < 0){
                    break;
                }
            }
            return result;
        }
    }

    function Material(id, amount, container, amountContainer){
        const materialId = id;
        const materialContainer = container;
        const amContainer = amountContainer;
        let materialAmount = amount;

        this.getId = function(){
            return materialId;
        }

        this.getAmount = function(){
            return materialAmount;
        }

        this.getContainer = function(){
            return materialContainer;
        }
    }
})();