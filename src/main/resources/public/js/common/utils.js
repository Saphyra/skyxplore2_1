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

function getActualTimeStamp(){
    return Math.floor(new Date().getTime() / 1000);
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

function displayNotificationNum(containerId, notificationNum){
    const container = document.getElementById(containerId);
        container.innerHTML = notificationNum;

    if(notificationNum == 0){
        container.parentNode.classList.remove("notification-container");
    }else{
        if(!container.parentNode.classList.contains("notification-container")){
            container.parentNode.classList.add("notification-container");
        }
    }
}

function setIntervalImmediate(callBack, interval){
    callBack();
    return setInterval(callBack, interval);
}