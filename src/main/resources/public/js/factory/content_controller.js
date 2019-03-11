(function ContentController(){
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");

    events.DISPLAY_CATEGORY = "display_category";

    const categoryCache = new Cache(loadItemsOfCategory);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DISPLAY_CATEGORY},
        function(event){
            displayCategory(event.getPayload());
        }
    ));

    function displayCategory(categoryId){
        const itemIds = getItemsOfCategoryOrdered(categoryId);
        const container = document.getElementById("content");
            container.innerHTML = "";

        for(let iIndex in itemIds){
            container.appendChild(createElement(itemIds[iIndex]));
        }

        function createElement(itemId){
            const container = document.createElement("DIV");
                container.classList.add("content-element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemId);

                const nameContainer = document.createElement("DIV");
                    nameContainer.classList.add("content-element-title");
                    nameContainer.innerHTML = Items.getItem(itemId).name;
            container.appendChild(nameContainer);

            return container;
        }

        function getItemsOfCategoryOrdered(categoryId){
            const itemIds = categoryCache.get(categoryId);
                itemIds.sort(function(a, b){
                    return Items.getItem(a).name.localeCompare(Items.getItem(b));
                });
            return itemIds;
        }
    }

    function loadItemsOfCategory(categoryId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.ITEMS_OF_CATEGORY, categoryId));
        return response.status == ResponseStatus.OK ? JSON.parse(response.body) : throwException("InvalidResponse", response.toString());
    }
})();