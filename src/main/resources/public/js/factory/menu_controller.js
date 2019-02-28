(function MenuController(){
    scriptLoader.loadScript("js/common/equipment/category_menu_display_service.js");

    events.DISPLAY_MENU = "display_menu";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_MENU},
        displayMenu
    ));

    function displayMenu(){
        categoryMenuDisplayService.displayCategories(
            "menu",
            "gamedata/categorylist/factory_categories.json",
            function(category){
                eventProcessor.processEvent(new Event(events.DISPLAY_CATEGORY, category.getId()));
            }
         )
    }
})();