function throwException(name, message){
    name = name == undefined ? "" : name;
    message = message == undefined ? "" : message;
    throw {name: name, message: message};
}

function getLanguage(){
    return navigator.language.toLowerCase().split("-")[0];
}

function isEmailValid(email){
    let result;
    if(email == null || email == undefined){
        result = false;
    }else if(email.indexOf("@") < 1){
        result = false;
    }else if(email.lenght < 4){
        result = false;
    }else if(email.indexOf(".") < 0){
        result = false;
    }else if(email.lastIndexOf(".") > email.length - 3){
        result = false;
    }else{
        result = true;
    }
    return result;
}

function switchTab(clazz, id){
    $("." + clazz).hide();
    $("#" + id).show();
}

function orderMapByProperty(map, orderFunction){
    const result = {};
        const entryList = [];
        for(let id in map){
            entryList.push(new Entry(id, map[id]));
        }
        entryList.sort(orderFunction);
        
        for(let eindex in entryList){
            result[entryList[eindex].getKey()] = entryList[eindex].getValue();
        }

    return result;
    
    function Entry(k, v){
        const key = k;
        const value = v;
        
        this.getKey = function(){
            return key;
        }
        
        this.getValue = function(){
            return value;
        }
    }
}