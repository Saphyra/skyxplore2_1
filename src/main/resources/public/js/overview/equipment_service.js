(function EquipmentService(){
    events.LOAD_EQUIPMENT = "load_equipment";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_EQUIPMENT},
        loadEquipment
    ));
    
    function loadEquipment(){
        const request = new Request(HttpMethod.GET, Mapping.SHIP_DATA);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(ship){
                displayShip(ship);
            }
        dao.sendRequestAsync(request);
    }
    
    function displayShip(ship){
        logService.log(ship, "info", "Loaded ship: ");
    }
})();