(function BlockCharacterController(){
    events.SEARCH_CHARACTERS_CAN_BE_BLOCKED_ATTEMPT = "search_characters_can_be_blocked_attempt";
    events.BLOCK_CHARACTER = "block_character";

    $(document).ready(init);

    let timeout = null;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEARCH_CHARACTERS_CAN_BE_BLOCKED_ATTEMPT},
        function(){
            if(timeout){
                clearTimeout(timeout);
            }

            timeout = setTimeout(searchCharactersCanBeBlocked, getSearchResultTimeout());
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_CHARACTER},
        function(event){
            blockCharacter(event.getPayload());
        }
    ));

    function searchCharactersCanBeBlocked(){
        const characterName = $("#block-character-name").val();

        if(characterName.length < 3){
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.GET_CHARACTERS_CAN_BE_BLOCKED, {value: characterName});
            request.convertResponse = function(response){
                const characters = JSON.parse(response.body);
                characters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});
                return characters;
            }
            request.processValidResponse = function(characters){
                displayCharacters(characters);
            }
        dao.sendRequestAsync(request);

        function displayCharacters(characters){
            const container = document.getElementById("block-character-search-result");
                container.innerHTML = "";

            characters.length == 0 ? $("#no-character-can-be-blocked").show() : $("#no-character-can-be-blocked").hide();

            for(let cIndex in characters){
                container.appendChild(createCharacter(characters[cIndex]));
            }

            function createCharacter(character){
                const container = document.createElement("DIV");
                    container.classList.add("character-can-be-blocked");

                    const nameCell = document.createElement("DIV");
                        nameCell.innerHTML = character.characterName;
                container.appendChild(nameCell);

                    const addFriendButton = document.createElement("BUTTON");
                        addFriendButton.classList.add("block-character-button");
                        addFriendButton.innerHTML = Localization.getAdditionalContent("block-character");
                        addFriendButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.BLOCK_CHARACTER, {characterId: character.characterId}));
                        }
                container.appendChild(addFriendButton);

                return container;
            }
        }
    }

    function blockCharacter(data){
        if(!confirm(Localization.getAdditionalContent("confirm-block-character"))){
            return;
        }

        const characterId = data.characterId;
        const listItem = data.listItem;

        const request = new Request(HttpMethod.POST, Mapping.BLOCK_CHARACTER, {value: characterId});
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("character-blocked"));
                if(listItem){
                    document.getElementById("friend-request-list").removeChild(listItem);
                }else{
                    eventProcessor.processEvent(new Event(events.OPEN_MAIN_LISTS));
                }

                document.getElementById("block-character-search-result").innerHTML = "";
                document.getElementById("block-character-name").value = "";
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        $("#block-character-name").keyup(function(){
            eventProcessor.processEvent(new Event(events.SEARCH_CHARACTERS_CAN_BE_BLOCKED_ATTEMPT));
        });
    }
})();