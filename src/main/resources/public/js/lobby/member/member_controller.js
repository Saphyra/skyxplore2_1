(function MemberController(){
    $(document).ready(init);

    function loadMembers(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LOBBY_MEMBERS);
            request.convertResponse = function(response){
                const characters = JSON.parse(response.body);
                    characters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});
                return characters;
            }
            request.processValidResponse = function(characters){
                logService.log(characters);
            }

        dao.sendRequestAsync(request);
    }

    function init(){
        loadMembers();
    }
})();