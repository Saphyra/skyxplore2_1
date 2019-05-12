(function CharacterIdQueryService(){
    let characterId = null;

    window.characterIdQueryService = new function(){
        this.getCharacterId = function(){
            if(characterId == null){
                characterId = dao.sendRequest(HttpMethod.GET, Mapping.GET_CHARACTER_ID).body;
            }

            return characterId;
        }
    }
})();