(function MemberController(){
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOBBY_LOADED},
        loadMembers,
        true
    ));

    function loadMembers(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LOBBY_MEMBERS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(characters){
                displayCharacters(characters);
            }

        dao.sendRequestAsync(request);
    }

    function displayCharacters(characters){
        const container = document.getElementById("members");

        for(let cIndex in characters){
            container.appendChild(createCharacterItem(characters[cIndex]));
        }

        function createCharacterItem(character){
            const lobbyDetails = pageController.getLobbyDetails();
            const container = document.createElement("DIV");
                container.id = createMemberId(character.characterId);
                container.classList.add("member");
                if(character.characterId === lobbyDetails.ownerId){
                    container.classList.add("owner");
                }

                const nameCell = document.createElement("DIV");
                    nameCell.innerHTML = character.characterName;
                    nameCell.classList.add("member-name");
            container.appendChild(nameCell);

            return container;
        }
    }

    function createMemberId(characterId){
        return "member-" + characterId;
    }
})();