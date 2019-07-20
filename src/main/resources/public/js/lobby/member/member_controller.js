(function MemberController(){
    scriptLoader.loadScript("/js/common/character_id_query_service.js");

    events.DISPLAY_CHARACTERS = "display_characters";
    events.MEMBER_LEFT = "member_left";
    events.READY_CHARACTER = "ready_character";
    events.UNREADY_CHARACTER = "unready_character";
    events.SET_READY = "set_ready";
    events.SET_UNREADY = "set_unready";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOBBY_LOADED},
        function(){
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
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_CHARACTERS},
        function(event){displayCharacters(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MEMBER_LEFT},
        function(event){removeCharacter(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.READY_CHARACTER},
        function readyCharacter(){
            const request = new Request(HttpMethod.POST, Mapping.READY_CHARACTER);
                request.processValidResponse = function(){
                    eventProcessor.processEvent(new Event(events.LOAD_LOBBY_EVENTS));
                }
            dao.sendRequestAsync(request);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.UNREADY_CHARACTER},
        function unreadyCharacter(){
            const request = new Request(HttpMethod.POST, Mapping.UNREADY_CHARACTER);
                request.processValidResponse = function(){
                    eventProcessor.processEvent(new Event(events.LOAD_LOBBY_EVENTS));
                }
            dao.sendRequestAsync(request);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SET_READY},
        function(event){
            const characterId = event.getPayload().characterId;
            document.getElementById(createMemberId(characterId)).classList.add("ready-member");

            const readyButton = document.getElementById(createReadyButtonId(characterId));
            if(readyButton){
                enchantReadyButton(readyButton, true);
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SET_UNREADY},
        function(event){
            const characterId = event.getPayload().characterId;
            document.getElementById(createMemberId(characterId)).classList.remove("ready-member");

            const readyButton = document.getElementById(createReadyButtonId(characterId));
            if(readyButton){
                enchantReadyButton(readyButton, false);
            }
        }
    ));

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

                if(character.ready){
                    container.classList.add("ready-member");
                }

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
                    }else{
                        const readyButton = document.createElement("BUTTON");
                            readyButton.id = createReadyButtonId(character.characterId);

                            enchantReadyButton(readyButton, character.ready);
                        buttonWrapper.appendChild(readyButton);
                    }

                    if(lobbyDetails.ownerId == characterIdQueryService.getCharacterId() && character.characterId !== characterIdQueryService.getCharacterId()){
                        const transferOwnershipButton = document.createElement("BUTTON");
                            transferOwnershipButton.innerHTML = Localization.getAdditionalContent("transfer-ownership");
                            transferOwnershipButton.onclick = function(){
                                transferOwnership(character.characterId);
                            }
                        buttonWrapper.appendChild(transferOwnershipButton);

                        const kickButton = document.createElement("BUTTON");
                            kickButton.innerHTML = Localization.getAdditionalContent("kick-member");
                            kickButton.onclick = function(){
                                kickFromLobby(character.characterId);
                            }
                        buttonWrapper.appendChild(kickButton);
                    }

            container.appendChild(buttonWrapper);
            return container;
        }
    }

    function enchantReadyButton(readyButton, ready){
        const contentKey = ready ? "unready-button" : "ready-button";
            readyButton.innerHTML = Localization.getAdditionalContent(contentKey);
            readyButton.onclick = function(){
                const eventName = ready ? events.UNREADY_CHARACTER : events.READY_CHARACTER;
                eventProcessor.processEvent(new Event(eventName));
            }
    }

    function transferOwnership(characterId){
        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.TRANSFER_OWNERSHIP, characterId));
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("OWNERSHIP_TRANSFERRED"));
                eventProcessor.processEvent(new Event(events.LOAD_LOBBY_EVENTS));
            }
        dao.sendRequestAsync(request);
    }

    function kickFromLobby(characterId){
        const request = new Request(HttpMethod.DELETE, Mapping.concat(Mapping.KICK_FROM_LOBBY, characterId));
            request.processValidResponse = function(){
                removeCharacter(characterId);
                notificationService.showSuccess(MessageCode.getMessage("KICKED_FROM_LOBBY"));
            }
        dao.sendRequestAsync(request);
    }

    function removeCharacter(characterId){
        const character = document.getElementById(createMemberId(characterId));
        if(character){
            document.getElementById("members").removeChild(character);
        }
    }

    function createMemberId(characterId){
        return "member-" + characterId;
    }

    function createReadyButtonId(characterId){
        return "ready-" + characterId;
    }
})();