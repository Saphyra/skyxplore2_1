(function ContentController(){
    scriptLoader.loadScript("/js/common/cache.js");
    scriptLoader.loadScript("/js/common/localization/items.js");
    scriptLoader.loadScript("/js/common/equipment/equipment_label_service.js");
    scriptLoader.loadScript("/js/common/equipment/item_cache.js");
    scriptLoader.loadScript("/js/factory/content/factory_element.js");


    events.DISPLAY_CATEGORY = "display_category";

    const categoryCache = new Cache(loadItemsOfCategory);

    let currentCategoryId = null;
    let factoryElements = [];

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.DISPLAY_CATEGORY
                || eventType == events.MATERIALS_LOADED
                || eventType == events.MONEY_CHANGED;
        },
        function(event){
            displayCategory(event.getPayload() || currentCategoryId);
        }
    ));

    function displayCategory(categoryId){
        if(!categoryId){
            return;
        }

        currentCategoryId = categoryId;
        const itemIds = getItemsOfCategoryOrdered(categoryId);
        const container = document.getElementById("content");
            container.innerHTML = "";
        factoryElements = [];

        for(let iIndex in itemIds){
            const itemId = itemIds[iIndex];

            if(itemCache.get(itemId).buildable){
                container.appendChild(createElement(itemId));
            }
        }

        function createElement(itemId){
            const builder = new FactoryElementBuilder(itemId);

            const container = createContainer(itemId);
                container.appendChild(createNameContainer(itemId));
                container.appendChild(createMaterialsContainer(itemId, builder));
                container.appendChild(createCostContainer(builder));
                container.appendChild(createConstructionTimeContainer(builder));
                container.appendChild(createAmountInput(builder));
                container.appendChild(createBuildButton(builder));

            factoryElements.push(builder.build());

            function createContainer(itemId){
                const container = document.createElement("DIV");
                    container.classList.add("content-element");
                    container.title = equipmentLabelService.assembleTitleOfItem(itemId);
                return container;
            }

            function createNameContainer(itemId){
                const nameContainer = document.createElement("DIV");
                    nameContainer.classList.add("content-element-title");
                    nameContainer.innerHTML = Items.getItem(itemId).name;
                return nameContainer;
            }

            function createMaterialsContainer(itemId, builder){
                const materialsContainer = document.createElement("DIV");
                    const requiredMaterials = getRequiredMaterialsOrdered(itemId);
                        builder.requiredMaterials(requiredMaterials);

                    for(let materialId in requiredMaterials){
                        materialsContainer.appendChild(createRequiredMaterial(materialId, builder));
                    }
                return materialsContainer;

                function getRequiredMaterialsOrdered(itemId){
                    const itemData = itemCache.get(itemId);
                    const materialMapping = {};

                    for(let materialId in itemData.materials){
                        materialMapping[materialId] = itemData.materials[materialId];
                    }

                    return orderMapByProperty(
                        materialMapping,
                        function(a, b){
                            return Items.getItem(a.getKey()).name.localeCompare(Items.getItem(b.getKey()).name);
                        }
                    );
                }

                function createRequiredMaterial(materialId, builder){
                    const requiredMaterialContainer = document.createElement("DIV");
                            requiredMaterialContainer.classList.add("required-material");
                            requiredMaterialContainer.title = equipmentLabelService.assembleTitleOfItem(materialId);

                        requiredMaterialContainer.appendChild(createSpan(Items.getItem(materialId).name));
                        requiredMaterialContainer.appendChild(createSpan(": "));

                            const requiredAmountLabel = createSpan();
                        requiredMaterialContainer.appendChild(requiredAmountLabel);

                        requiredMaterialContainer.appendChild(createSpan(" / "));

                            const storedMaterialAmountLabel = createSpan();
                        requiredMaterialContainer.appendChild(storedMaterialAmountLabel);

                        const materialElement = new MaterialElement(materialId, requiredAmountLabel, storedMaterialAmountLabel);
                        builder.addMaterialElement(materialElement);

                        return requiredMaterialContainer;
                }
            }

            function createCostContainer(builder){
                const costContainerWrapper = document.createElement("DIV");
                    const costContainer = createSpan();
                        costContainer.classList.add("cost-container");

                        const costLabel = document.createElement("SPAN");
                            costLabel.innerHTML = Localization.getAdditionalContent("cost") + ": ";
                    costContainer.appendChild(costLabel);

                        const costAmount = createSpan();
                            builder.costLabel(costAmount);
                    costContainer.appendChild(costAmount);

                    costContainer.appendChild(createSpan(" / "));

                        const moneyLabel = createSpan();
                            builder.moneyLabel(moneyLabel);
                    costContainer.appendChild(moneyLabel);
                costContainerWrapper.appendChild(costContainer);
                return costContainerWrapper;
            }

            function createConstructionTimeContainer(builder){
                const constructionTimeContainer = document.createElement("DIV");
                    constructionTimeContainer.classList.add("construction-time-container");

                constructionTimeContainer.appendChild(createSpan(Localization.getAdditionalContent("construction-time") + ": "));

                    const constructionTimeLabel = createSpan();
                        builder.constructionTimeLabel(constructionTimeLabel);
                constructionTimeContainer.appendChild(constructionTimeLabel);

                return constructionTimeContainer;
            }

            function createAmountInput(builder){
                const amountContainer = document.createElement("LABEL");
                    amountContainer.classList.add("amount-container");
                    amountContainer.appendChild(createSpan(Localization.getAdditionalContent("amount") + ": "));

                    const amountInput = document.createElement("INPUT");
                        amountInput.type = "number";
                        amountInput.min = 1;
                        amountInput.value = 1;
                        amountInput.placeholder = Localization.getAdditionalContent("amount");
                        builder.amountInput(amountInput);
                amountContainer.appendChild(amountInput);

                return amountContainer;
            }

            function createBuildButton(builder){
                const buildButton = document.createElement("BUTTON");
                    buildButton.innerHTML = Localization.getAdditionalContent("start-construction");
                    builder.buildButton(buildButton);
                return buildButton;
            }
            return container;
        }

        function getItemsOfCategoryOrdered(categoryId){
            const itemIds = categoryCache.get(categoryId);
                itemIds.sort(function(a, b){
                    return Items.getItem(a).name.localeCompare(Items.getItem(b).name);
                });
            return itemIds;
        }
    }

    function loadItemsOfCategory(categoryId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.ITEMS_OF_CATEGORY, categoryId));
        return response.status == ResponseStatus.OK ? JSON.parse(response.body) : throwException("InvalidResponse", response.toString());
    }
})();