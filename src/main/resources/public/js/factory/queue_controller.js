(function QueueController(){
    window.queueController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        this.queue = [];
        
        this.addToQueue = addToQueue;
        this.displayQueue = displayQueue;
        this.loadQueue = loadQueue;
    }
    
    /*
    Adds the selected element to the factory queue.
    Arguments:
        - elementId: the id of the element to add.
        - amount: the amount
    Throws:
        - UnknownServerError exception if request fails
    */
    function addToQueue(elementId, amount){
        try{
            const result = factoryDao.addToQueue(sessionStorage.characterId, elementId, amount);
            if(result.status == 200){
                notificationService.showSuccess("Megrendelés elküldve.");
            }else{
                throwException("UnknownServerError", result.toString());
            }
            pageController.refresh(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    function displayQueue(){
        try{
            const container = document.getElementById("queue");
                container.innerHTML = "";
                
            if(queueController.queue.length == 0){
                container.appendChild(createEmptyMessage());
            }else{
                for(let qindex in queueController.queue){
                    container.appendChild(createQueueElement(queueController.queue[qindex]));
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createEmptyMessage(){
            try{
                const element = document.createElement("DIV");
                    element.innerHTML = "A gyártósor üres.";
                    element.classList.add("textaligncenter");
                    element.classList.add("margintop0_5rem");
                    element.classList.add("fontsize1_5rem");
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function createQueueElement(element){
            try{
                const elementData = cache.get(element.elementId);
                const container = document.createElement("DIV");
                    container.classList.add("queueelement");
                
                    const title = document.createElement("DIV");
                        title.classList.add("queueelementtitle");
                        title.innerHTML = elementData.name + " x " + element.amount;
                container.appendChild(title);
                
                    const processContainer = document.createElement("DIV");
                        processContainer.classList.add("queueprocess");
                        if(element.startTime != null){
                            const processBar = document.createElement("DIV");
                                processBar.classList.add("processbar");
                            processContainer.appendChild(processBar);
                            
                            const textContainer = document.createElement("DIV");
                                textContainer.classList.add("processbartext");
                            processContainer.appendChild(textContainer);
                            
                            const interval = setInterval(function(){
                                const timeLeft = countTimeLeft(element.endTime);
                                
                                const processRate = 100 - timeLeft / element.constructionTime * 100;
                                processBar.style.width = processRate + "%";
                                
                                textContainer.innerHTML = translator.convertTimeStamp(timeLeft);
                                
                                if(timeLeft == 0){
                                    clearInterval(interval);
                                    setTimeout(function(){
                                        loadQueue();
                                        displayQueue();
                                    }, 11000);
                                }
                            }, 1000);
                        }else{
                            processContainer.innerHTML = "Sorban áll";
                        }
                container.appendChild(processContainer);
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
            
            function countTimeLeft(endTime){
                try{
                    const now = getActualTimeStamp();
                    const result = endTime - now;
                    return result < 0 ? 0 : result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return 0;
                }
            }
        }
    }

    /*
    Queries the queue from the server, and orders by startTime, and addedAt
    */
    function loadQueue(){
        try{
            const result = factoryDao.getQueue(sessionStorage.characterId);
            
            result.sort(function(a, b){
                if(a.startTime != null){
                    return -1;
                }
                return a.addedAt - b.addedAt;
            })
            
            queueController.queue = result;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();