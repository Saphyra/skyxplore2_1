(function QueueController(){
    scriptLoader.loadScript("js/common/localization/date_time_formatter.js");

    events.LOAD_QUEUE = "load_queue";
    events.PRODUCT_FINISHED = "product_finished";
    events.ADD_TO_QUEUE = "add_to_queue";
    events.ADDED_TO_QUEUE = "added_to_queue";

    let reloadTimeout = null;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.LOAD_QUEUE
                || eventType == events.ADDED_TO_QUEUE
                || eventType == events.PRODUCT_FINISHED
        },
        loadQueue
    ));

    eventProcessor.registerProcessor(new EventProcessor(
            function(eventType){return eventType == events.ADD_TO_QUEUE},
            function(event){
                addToQueue(event.getPayload().itemId, event.getPayload().amount);
            }
    ));

    function loadQueue(){
        $("#empty-queue").show();
        document.getElementById("queue").innerHTML = "";

        const request = new Request(HttpMethod.GET, Mapping.GET_QUEUE);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(queue){
                queue.sort(function(a, b){
                    if(a.startTime != null){
                        return -1;
                    }
                    return a.addedAt - b.addedAt;
                });
                for(let qIndex in queue){
                    displayQueueElement(queue[qIndex]);
                }
            }
        dao.sendRequestAsync(request);
    }

    function displayQueueElement(queueItem){
        $("#empty-queue").hide();
        const container = document.createElement("DIV");
            container.classList.add("queue-element");

            const titleContainer = document.createElement("DIV");
                titleContainer.classList.add("queue-element-title");

                titleContainer.appendChild(createSpan(Items.getItem(queueItem.elementId).name));
                titleContainer.appendChild(createSpan(" x "));
                titleContainer.appendChild(createSpan(queueItem.amount));
        container.appendChild(titleContainer);

            const processContainer = document.createElement("DIV");
                processContainer.classList.add("queue-process");
                if(queueItem.startTime == null){
                    processContainer.innerHTML = Localization.getAdditionalContent("waiting");
                }else{
                    const processBar = document.createElement("DIV");
                        processBar.classList.add("process-bar");
                    processContainer.appendChild(processBar);

                    const textContainer = document.createElement("DIV");
                        textContainer.classList.add("process-bar-text");
                    processContainer.appendChild(textContainer);

                    const interval = setInterval(
                        function(){
                            const timeLeft = countTimeLeft(queueItem.endTime);

                            const processRate = 100 - timeLeft / queueItem.constructionTime * 100;
                            processBar.style.width = processRate + "%";

                            textContainer.innerHTML = dateTimeFormatter.convertTimeStamp(timeLeft);

                            if(timeLeft == 0){
                                clearInterval(interval);

                                setTimeout(
                                    function(){
                                        eventProcessor.processEvent(new Event(events.PRODUCT_FINISHED));
                                    },
                                    11000
                                )
                            }

                            function countTimeLeft(endTime){
                                const now = getActualTimeStamp();
                                const result = endTime - now;
                                return result < 0 ? 0 : result;
                            }
                        },
                        1000
                    );
                }
        container.appendChild(processContainer);
        document.getElementById("queue").appendChild(container);
    }

    function addToQueue(itemId, amount){
        const content = {
            elementId: itemId,
            amount: amount
        };
        const request = new Request(HttpMethod.PUT, Mapping.ADD_TO_QUEUE, content);
            request.processValidResponse = function(){
                eventProcessor.processEvent(new Event(events.ADDED_TO_QUEUE));
                if(reloadTimeout){
                    clearTimeout(reloadTimeout)
                }
                reloadTimeout = setTimeout(
                    function(){eventProcessor.processEvent(new Event(events.LOAD_QUEUE))},
                    11000
                )
            }
        dao.sendRequestAsync(request);
    }
})();