(function NotificationService(){
    window.notificationService = new function(){
        scriptLoader.loadScript("js/common/animation/animationfacade.js");
        
        this.showSuccess = showSuccess;
        this.showError = showError;
        this.showMessage = showMessage;
    }
    
    //TODO documentation
    function showSuccess(message){
        try{
            showMessage(message, "green");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    //TODO documentation
    function showError(message){
        try{
            showMessage(message, "red");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    //TODO documentation
    function showMessage(message, bgColor){
        try{
            const container = document.getElementById("notificationcontainer") || createContainer();
            const messageElement = createMessageElement(message, bgColor);
            messageElement.onclick = function(){
                container.removeChild(messageElement);
            }
            animationFacade.rollInHorizontal(messageElement, container, 300);
            setTimeout(function(){animationFacade.rollOutHorizontal(messageElement, container, 300)}, 10000);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        //TODO documentation
        function createContainer(){
            try{
                const container = document.createElement("DIV");
                    container.id = "notificationcontainer";
                    container.classList.add("fixed");
                    container.classList.add("left5px");
                    container.classList.add("top5px");
                    container.style.maxWidth = "calc(100vw - 10px)";
                document.body.appendChild(container);
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        //TODO documentation
        function createMessageElement(message, bgColor){
            try{
                const wrapper = document.createElement("DIV");
                    wrapper.classList.add("inlineblock");
                    const element = document.createElement("DIV");
                        element.innerHTML = message;
                        element.style.backgroundColor = bgColor;
                        element.classList.add("button");
                        element.classList.add("fontsize1_5rem");
                        element.classList.add("margin0_25rem");
                        element.classList.add("padding0_25rem");
                        element.classList.add("whitespacenowrap");
                wrapper.appendChild(element);
                return wrapper;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();