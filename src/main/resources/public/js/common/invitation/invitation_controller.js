(function InvitationController(){
    scriptLoader.loadScript("js/common/localization/localization_map.js");
    scriptLoader.loadScript("js/common/game/game_mode_translation.js");

    const gameModeLocalization = localizationMapCache.getLocalizationMap("game_mode");
    const invitationLocalization = localizationMapCache.getLocalizationMap("invitation");
    $(document).ready(init);

    function loadInvitations(){
        const request = new Request(HttpMethod.GET, Mapping.GET_INVITATIONS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(invitations){
                displayInvitations(invitations);
            }

        dao.sendRequestAsync(request);

        function displayInvitations(invitations){
            const container = getOrCreateContainer();

            for(let iIndex in invitations){
                const invitation = createInvitation(invitations[iIndex]);
                setTimeout(function(){removeInvitation(invitation)}, 60000);
                container.appendChild(invitation);
            }

            function getOrCreateContainer(){
                const container = document.getElementById("invitation-container");
                if(!container){
                    const newContainer = document.createElement("DIV");
                        newContainer.id = "invitation-container"
                    document.body.appendChild(newContainer);
                    return newContainer;
                }

                return container;
            }

            function createInvitation(invitation){
                const container = document.createElement("DIV");
                    container.classList.add("invitation");

                    const title = document.createElement("DIV");
                        title.classList.add("invitation-title");
                        const characterName = document.createElement("SPAN");
                            characterName.innerHTML = invitation.characterName;
                    title.appendChild(characterName);

                        const titleDelimiter = document.createElement("SPAN");
                            titleDelimiter.innerHTML = " ";
                    title.appendChild(titleDelimiter);

                        const invited = document.createElement("SPAN");
                            const gameMode = gameModeLocalization.getLocalization(invitation.gameMode);
                            const titleText = invitationLocalization.getLocalization("title-text");
                            invited.innerHTML = titleText.replace("*", gameMode);
                    title.appendChild(invited);
                container.appendChild(title);

                    if(invitation.data){
                        const data = document.createElement("DIV");
                            data.classList.add("invitation-data");
                            const dataSpan = document.createElement("SPAN");
                                dataSpan.innerHTML = gameModeTranslation.getDataName(invitation.gameMode);
                        data.appendChild(dataSpan);

                            const dataDelimiter = document.createElement("SPAN");
                                dataDelimiter.innerHTML = "";
                        data.appendChild(dataDelimiter);

                            const value = document.createElement("SPAN");
                                value.innerHTML = gameModeTranslation.translateData(invitation.gameMode, invitation.data);
                        data.appendChild(value);
                        container.appendChild(data);

                    }

                    const buttons = document.createElement("DIV");
                        buttons.classList.add("invitation-buttons");

                        const declineButton = document.createElement("BUTTON");
                            declineButton.innerHTML = invitationLocalization.getLocalization("decline-invitation");
                            declineButton.onclick = function(){
                                removeInvitation(container);
                            }
                    buttons.appendChild(declineButton);

                        const profileButton = document.createElement("BUTTON");
                            profileButton.innerHTML = invitationLocalization.getLocalization("profile");
                    buttons.appendChild(profileButton);

                        const acceptButton = document.createElement("BUTTON");
                            acceptButton.innerHTML = invitationLocalization.getLocalization("accept-invitation");
                    buttons.appendChild(acceptButton);

                container.appendChild(buttons);

                return container;
            }

            function removeInvitation(invitation){
                document.getElementById("invitation-container").removeChild(invitation);
            }
        }
    }

    function init(){
        setInterval(loadInvitations, 5000);
        addCss();

        function addCss(){
            const element = document.createElement("link");
                element.setAttribute("rel", "stylesheet");
                element.setAttribute("type", "text/css");
                element.setAttribute("href", "css/invitation.css");
            document.getElementsByTagName("head")[0].appendChild(element);
        }
    }
})();