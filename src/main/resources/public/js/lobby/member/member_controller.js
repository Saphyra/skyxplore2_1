(function MemberController(){
    scriptLoader.loadScript("js/common/character_id_query_service.js");

    events.DISPLAY_CHARACTERS = "display_characters";
    events.MEMBER_LEFT = "member_left";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOBBY_LOADED},
        loadMembers
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_CHARACTERS},
        function(event){displayCharacters(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MEMBER_LEFT},
        function(event){removeCharacter(event.getPayload())}
    ));

    function loadMembers(){
        document.getElementById("members").innerHTML = "";
        const request = new Request(HttpMethod.GET, Mapping.GET_LOBBY_MEMBERS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(characters){
                eventProcessor.processEvent(new Event(events.DISPLAY_CHARACTERS, characters));
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

                const buttonWrapper = document.createElement("DIV");
                    if(character.characterId !== characterIdQueryService.getCharacterId()){
                        const profileButton = document.createElement("BUTTON");
                            profileButton.innerHTML = Localization.getAdditionalContent("visit-profile");
                        buttonWrapper.appendChild(profileButton);
                    }

                    if(lobbyDetails.ownerId == characterIdQueryService.getCharacterId() && character.characterId !== characterIdQueryService.getCharacterId()){
                        const transferOwnershipButton = document.createElement("BUTTON");
                            transferOwnershipButton.innerHTML = Localization.getAdditionalContent("transfer-ownership");
                        buttonWrapper.appendChild(transferOwnershipButton);

                        const kickButton = document.createElement("BUTTON");
                            kickButton.innerHTML = Localization.getAdditionalContent("kick-member");
                        buttonWrapper.appendChild(kickButton);
                    }

            container.appendChild(buttonWrapper);
            return container;
        }
    }

    function removeCharacter(characterId){
        document.getElementById("members").removeChild(document.getElementById(createMemberId(characterId)));
    }

    function createMemberId(characterId){
        return "member-" + characterId;
    }
})();