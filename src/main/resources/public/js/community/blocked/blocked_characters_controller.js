(function BlockedCharactersController(){
    events.ALLOW_CHARACTER = "allow_character"

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

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_CHARACTER},
        function(event){
            allowCharacter(event.getPayload());
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
                    container.id = generateBlockedCharacterId(blockedCharacter.characterId);

                    const characterName = document.createElement("DIV");
                        characterName.innerHTML = blockedCharacter.characterName;
                container.appendChild(characterName);

                    const allowButton = document.createElement("BUTTON");
                        allowButton.innerHTML = Localization.getAdditionalContent("allow-blocked-character");
                        allowButton.classList.add("friend-list-item-button");
                        allowButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.ALLOW_CHARACTER, blockedCharacter.characterId));
                        }
                container.appendChild(allowButton);

                return container;
            }
        }
    }

    function allowCharacter(characterId){
        const request = new Request(HttpMethod.DELETE, Mapping.ALLOW_CHARACTER, {value: characterId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("CHARACTER_ALLOWED"));
                document.getElementById("blocked-character-list").removeChild(document.getElementById(generateBlockedCharacterId(characterId)));
            }

        dao.sendRequestAsync(request);
    }

    function generateBlockedCharacterId(characterId){
        return "blocked-character-" + characterId;
    }
})();