(function MenuController(){
    scriptLoader.loadScript("js/common/localization/category_names.js");
    
    events.DISPLAY_MENU = "display_menu";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_MENU},
        displayMenu
    ));
    
    function displayMenu(){
        const container = document.getElementById("menu");
        const categories = getCategories();
        const categoryMapping = mapCategories(categories);
        
        for(let cindex in categoryMapping){
            container.appendChild(createParentCategory(categoryMapping[cindex]));
        }
        
        function createParentCategory(category){
            const parentCategory = category.getParent();
            const subCategories = category.getSubCategories();
            
            const container = document.createElement("DIV");
                container.classList.add("menu-item");
                
                const parentButton = document.createElement("DIV");
                    parentButton.classList.add("menu-group-title");
                    parentButton.innerHTML = parentCategory.getName();
            container.appendChild(parentButton);
            
                const childContainer = document.createElement("DIV");
                    childContainer.classList.add("menu-child");
               
                    for(let sindex in subCategories){
                        childContainer.appendChild(createSubCategory(subCategories[sindex]));
                    }
               
            container.appendChild(childContainer);
            return container;
            
            function createSubCategory(category){
                const subCategoryContainer = document.createElement("DIV");
                    subCategoryContainer.classList.add("button")
                    subCategoryContainer.innerHTML = category.getName();
                    subCategoryContainer.onclick = function(){
                        eventProcessor.processEvent(new Event(events.DISPLAY_CATEGORY, category.getId()));
                    }
                return subCategoryContainer;
                    
            }
        }
        
        function getCategories(){
            const response = dao.sendRequest(HttpMethod.GET, "gamedata/categorylist/shop_categories.json");
            return response.status == ResponseStatus.OK ? JSON.parse(response.body) : throwException("InvalidResponse", response.toString);
        }
        
        function mapCategories(categories){
            const result = [];
            for(let categoryId in categories){
                const categoryName = CategoryNames.getCategoryName(categoryId);
                const category = new Category(categoryId, categoryName);
                
                const subCategoryIds = categories[categoryId];
                const subCategories = [];
                
                for(let subCategoryIndex in subCategoryIds){
                    const subCategoryId = subCategoryIds[subCategoryIndex];
                    const subCategoryName = CategoryNames.getCategoryName(subCategoryId);
                    subCategories.push(new Category(subCategoryId, subCategoryName));
                }
                
                subCategories.sort(function(a, b){
                    return a.getName().localeCompare(b.getName());
                });
                result.push(new MainCategory(category, subCategories));
            }
            
            result.sort(function(a, b){
                return a.getParent().getName().localeCompare(b.getParent().getName());
            });
            
            return result;
        }
    }
    
    function MainCategory(p, s){
        const parent = p;
        const subCategories = s;
        
        this.getParent = function(){return parent};
        this.getSubCategories = function(){return subCategories};
    }
    
    function Category(categoryId, categoryName){
        const id = categoryId;
        const name = categoryName;
        
        this.getId = function(){return id};
        this.getName = function(){return name};
        this.toString = function(){return "Category: [id: " + id  + "], [name: " + name + "]"};
    }
})();