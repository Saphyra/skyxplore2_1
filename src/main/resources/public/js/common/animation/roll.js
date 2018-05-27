(function Roll(){
    window.roll = new function(){
        scriptLoader.loadScript("js/common/animation/domutil.js");
        this.rollInHorizontal = rollInHorizontal;
        this.rollOutHorizontal = rollOutHorizontal;
    }
    
    function rollInHorizontal(element, container, time){
        try{
            const width = DOMUtil.getElementWidth(element, container);
                element.style.overflow = "hidden";
                element.style.width = 0;
                
                const timeout = Math.round(time / width) * 3;
                
                let actualWidth = 0;
                const interval = setInterval(function(){
                    try{
                        actualWidth += 3;
                        element.style.width = actualWidth + "px";
                        if(actualWidth >= width){
                            clearInterval(interval);
                        }
                    }catch(err){
                        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                        logService.log(message, "error");
                    }
                }, timeout);
                
            container.appendChild(element);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function rollOutHorizontal(element, container, time){
        try{
            const timeout = Math.round(time / element.offsetWidth) * 3;
            
            let actualWidth = element.offsetWidth;
            const interval = setInterval(function(){
                actualWidth -= 3;
                element.style.width = actualWidth + "px";
                if(actualWidth <= 0){
                    clearInterval(interval);
                }
            }, timeout);
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();