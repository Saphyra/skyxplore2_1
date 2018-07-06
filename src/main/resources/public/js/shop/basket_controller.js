(function BasketController(){
    window.basketController = new function(){
        this.basket = {};
        
        this.addElement = addElement;
        this.buyItems = buyItems;
        this.displayBasket = displayBasket;
        this.getBasketCost = getBasketCost;
        this.removeElement = removeElement;
        
        $(document).ready(function(){
            displayBasket();
        });
    }

    /*
    Adds an element to the basket.
    Arguments:
        - elementId: the id of the element to add.
    Throws:
        - IllegalArgumentException if elementId is null or undefined.
    */
    function addElement(elementId){
        try{
            if(elementId == null || elementId == undefined){
                throwException("IllegalArgument", "elementId must not be null or undefined.");
            }

            if(!basketController.basket[elementId]){
                basketController.basket[elementId] = 0;
            }
            basketController.basket[elementId]++;
            displayBasket();
            contentController.displayElements(null, false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    /*
    Buys the items in the basket.
    Throws:
        - UnknownServerError exception if request fails.
    */
    function buyItems(){
        try{
            const result = characterDao.buyItems(basketController.basket);
            if(result.status == 200){
                basketController.basket = {};
                pageController.refresh(null, true);
                notificationService.showSuccess("Tárgyak megvásárolva.");
            }else{
                throwException("UnknownServerError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    /*
    Displays the content of the basket.
    */
    function displayBasket(){
        try{
            const container = document.getElementById("basket");
                container.innerHTML = "";
            const costField = document.getElementById("cost");
            
            let cost = 0;
            
            for(let elementId in basketController.basket){
                const amount = basketController.basket[elementId];
                if(amount > 0){
                    const element = cache.get(elementId);
                    cost += amount * element.buyprice;
                    container.appendChild(createBasketElement(element, amount));
                }
            }
            
            if(cost == 0){
                container.appendChild(createNoElement());
            }else{
                container.appendChild(createBuyButton());
            }
            
            costField.innerHTML = cost;
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createBasketElement(element, amount){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("basketelement");
                    container.title = titleService.getTitleForOverview(element.id);
                    
                    const title = document.createElement("DIV");
                        title.classList.add("basketelementtitle");
                        title.innerHTML = element.name + " x " + amount;
                container.appendChild(title);
                
                    const priceContainer = document.createElement("DIV");
                        priceContainer.classList.add("margintop0_25rem");
                        priceContainer.innerHTML = "Ár: " + element.buyprice + " x " + amount + " = " + element.buyprice * amount;
                container.appendChild(priceContainer);
                
                    const cancelButton = document.createElement("BUTTON");
                        cancelButton.innerHTML = "Mégse";
                        cancelButton.onclick = function(){removeElement(element.id)};
                container.appendChild(cancelButton);
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function createBuyButton(){
            try{
                const element = document.createElement("BUTTON");
                    element.innerHTML = "Vásárlás";
                    element.classList.add("fontsize1_5rem");
                    element.onclick = function(){buyItems()};
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function createNoElement(){
            try{
                const element = document.createElement("DIV");
                    element.innerHTML = "A kosár üres!";
                    
                    element.classList.add("fontsize1_5rem");
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }

    /*
    Returns: the sum of all items' cost in the basket.
    */
    function getBasketCost(){
        try{
            let cost = 0;
            
            for(let elementId in basketController.basket){
                const amount = basketController.basket[elementId];
                const element = cache.get(elementId);
                cost += amount * element.buyprice;
            }
            
            return cost;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    /*
    Removes the specified element from the basket.
    Arguments:
        - elementId: the id of the element.
    Throws:
        - IllegalArgument exception if elementId is null or undefined.
        - IllegalState exception if basket does not contain the specified element.
    */
    function removeElement(elementId){
        try{
            if(elementId == null || elementId == undefined){
                throwException("IllegalArgument", "elementId must not be null or undefined.");
            }
            if(!basketController.basket[elementId]){
                throwException("IllegalState", "basket does not contain element with id " + elementId);
            }

            basketController.basket[elementId]--;
            displayBasket();
            contentController.displayElements(null, false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();