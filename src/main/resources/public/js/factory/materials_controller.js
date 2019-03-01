(function MaterialsController(){
    scriptLoader.loadScript("js/common/localization/items.js");

    events.LOAD_MATERIALS = "load_materials";

    let materials = [];

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_MATERIALS},
        loadMaterials
    ));

    function loadMaterials(){
        const request = new Request(HttpMethod.GET, Mapping.GET_MATERIALS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(payload){
                for(let materialId in payload){
                    displayMaterial(materialId, payload[materialId]);
                }
            }

        dao.sendRequestAsync(request);
    }

    function displayMaterial(materialId, amount){

    }

    function Material(id, amount){

    }
})();