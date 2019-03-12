(function ContentController(){
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/localization/date_time_formatter.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    scriptLoader.loadScript("js/common/equipment/item_cache.js");

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
            const itemData = itemCache.get(itemId);
            const container = document.createElement("DIV");
                container.classList.add("content-element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemId);

                const nameContainer = document.createElement("DIV");
                    nameContainer.classList.add("content-element-title");
                    nameContainer.innerHTML = Items.getItem(itemId).name;
            container.appendChild(nameContainer);

                const materialsContainer = document.createElement("DIV");
                    const requiredMaterials = getRequiredMaterialsOrdered(itemId);

                    for(let materialId in requiredMaterials){
                        const requiredAmount = requiredMaterials[materialId];
                        const materialData = itemCache.get(materialId);
                        const requiredMaterialContainer = document.createElement("DIV");
                            requiredMaterialContainer.classList.add("required-material");
                            requiredMaterialContainer.title = equipmentLabelService.assembleTitleOfItem(materialId);

                            const nameLabel = document.createElement("SPAN");
                                nameLabel.innerHTML = Items.getItem(materialId).name;
                        requiredMaterialContainer.appendChild(nameLabel);

                            const nameDelimiter = document.createElement("SPAN");
                                nameDelimiter.innerHTML = ": ";
                        requiredMaterialContainer.appendChild(nameDelimiter);

                            const requiredAmountLabel = document.createElement("SPAN");
                                requiredAmountLabel.innerHTML = requiredAmount;
                        requiredMaterialContainer.appendChild(requiredAmountLabel);

                            const delimiter = document.createElement("SPAN");
                                delimiter.innerHTML = " / ";
                        requiredMaterialContainer.appendChild(delimiter);

                            const storedMaterialAmount = materialsController.getMaterialAmount(materialId);
                            const storedMaterialAmountLabel = document.createElement("SPAN");
                                storedMaterialAmountLabel.innerHTML = storedMaterialAmount;
                        requiredMaterialContainer.appendChild(storedMaterialAmountLabel);

                        materialsContainer.appendChild(requiredMaterialContainer);
                    }
            container.appendChild(materialsContainer);

                const costContainer = document.createElement("DIV");
                    costContainer.classList.add("cost-container");

                    const costLabel = document.createElement("SPAN");
                        costLabel.innerHTML = Localization.getAdditionalContent("cost") + ": ";
                costContainer.appendChild(costLabel);

                    const costAmount = document.createElement("SPAN");
                        costAmount.innerHTML = itemData.buildprice;
                costContainer.appendChild(costAmount);

                    const costDelimiter = document.createElement("SPAN");
                        costDelimiter.innerHTML = " / ";
                costContainer.appendChild(costDelimiter);

                    const moneyLabel = document.createElement("SPAN");
                        moneyLabel.innerHTML = moneyController.getMoney();
                costContainer.appendChild(moneyLabel);
            container.appendChild(costContainer);

                const constructionTimeContainer = document.createElement("DIV");
                    constructionTimeContainer.classList.add("construction-time-container");

                    const constructionTimeTitle = document.createElement("SPAN");
                        constructionTimeTitle.innerHTML = Localization.getAdditionalContent("construction-time") + ": ";
                constructionTimeContainer.appendChild(constructionTimeTitle);

                    const constructionTimeLabel = document.createElement("SPAN");
                        constructionTimeLabel.innerHTML = dateTimeFormatter.convertTimeStamp(itemData.constructiontime);
                constructionTimeContainer.appendChild(constructionTimeLabel);
            container.appendChild(constructionTimeContainer);

                const amountContainer = document.createElement("LABEL");
                    amountContainer.classList.add("amount-container");
                    amountContainer.appendChild(document.createTextNode(Localization.getAdditionalContent("amount") + ": "));

                    const amountInput = document.createElement("INPUT");
                        amountInput.type = "number";
                        amountInput.min = 1;
                        amountInput.value = 1;
                        amountInput.placeholder = Localization.getAdditionalContent("amount");
                amountContainer.appendChild(amountInput);
            container.appendChild(amountContainer);

                const buildButton = document.createElement("BUTTON");
                    buildButton.innerHTML = Localization.getAdditionalContent("start-construction");
            container.appendChild(buildButton);

            return container;
        }

        function getItemsOfCategoryOrdered(categoryId){
            const itemIds = categoryCache.get(categoryId);
                itemIds.sort(function(a, b){
                    return Items.getItem(a).name.localeCompare(Items.getItem(b).name);
                });
            return itemIds;
        }

        function getRequiredMaterialsOrdered(itemId){
            const itemData = itemCache.get(itemId);
            const materialMapping = {};

            for(let materialId in itemData.materials){
                materialMapping[materialId] = itemData.materials[materialId];
            }

            return orderMapByProperty(
                materialMapping,
                function(a, b){
                    return Items.getItem(a.getKey()).name.localeCompare(Items.getItem(b.getKey())).name;
                }
            );
        }
    }

    function loadItemsOfCategory(categoryId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.ITEMS_OF_CATEGORY, categoryId));
        return response.status == ResponseStatus.OK ? JSON.parse(response.body) : throwException("InvalidResponse", response.toString());
    }
})();