(function Translator(){
    window.translator = new function(){
        this.translateSlot = translateSlot;
        this.translateType = translateType;
    }
    
    function translateSlot(slot){
        try{
            switch(slot){
                case "connector":
                    return "Csatlakozó";
                break;
                case "defense":
                    return "Védelem";
                break;
                case "ship":
                    return "Hajó";
                break;
                case "weapon":
                    return "Fegyver";
                break;
                default:
                    throwException("IllegalArgument", "Unknown slot: " + slot);
                break;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function translateType(type){
        try{
            switch(type){
                default:
                    throwException("IllegalArgument", "Unknown type: " + type);
                break;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();