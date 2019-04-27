(function BlockedCharactersController(){
    let isActive = true;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_BLOCKED_CHARACTERS_TAB},
        function(){
            loadBlockedCharacters();
            isActive = true;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_FRIEND_CHARACTERS_TAB},
        function(){
            isActive = false;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.OPEN_MAIN_LISTS
                && isActive
        },
        function(){
            loadBlockedCharacters()
        }
    ));

    function loadBlockedCharacters(){
        const request = new Request(HttpMethod.GET, Mapping.GET_BLOCKED_CHARACTERS);
            request.convertResponse = function(response){
                const blockedCharacters = JSON.parse(response.body);
                blockedCharacters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});
                return blockedCharacters;
            }
            request.processValidResponse = function(blockedCharacters){
                displayBlockedCharacters(blockedCharacters);
            }
        dao.sendRequestAsync(request);

        function displayBlockedCharacters(blockedCharacters){
            blockedCharacters.length == 0 ? $("#no-blocked-characters").show() : $("#no-blocked-characters").hide();

            const container = document.getElementById("blocked-character-list");
                container.innerHTML = "";

            for(let bIndex in blockedCharacters){
                container.appendChild(createBlockedCharacter(blockedCharacters[bIndex]));
            }

            function createBlockedCharacter(blockedCharacter){
                const container = document.createElement("DIV");
                    container.classList.add("friend-list-item");

                    const characterName = document.createElement("DIV");
                        characterName.innerHTML = blockedCharacter.characterName;
                container.appendChild(characterName);
                return container;
            }
        }
    }
})();