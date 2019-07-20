(function ItemCache(){
    scriptLoader.loadScript("/js/common/cache.js");
    
    window.itemCache = new Cache(loadItem);
    
    function loadItem(itemId){
        const response = dao.sendRequest(HttpMethod.GET, "/gamedata/items/" + itemId + ".json");
        if(response.status == ResponseStatus.OK){
            return JSON.parse(response.body);
        }else{
            throwException("UnknownResponse", response.toString());
        }
    }
})();