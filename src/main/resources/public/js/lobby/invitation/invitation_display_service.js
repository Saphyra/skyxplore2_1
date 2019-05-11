(function InvitationDisplayService(){
    events.DISPLAY_INVITATION = "display_invitation";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DISPLAY_INVITATION},
        function(event){displayInvitation(event.getPayload())}
    ));

    function displayInvitation(characters){
        characters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});

        const container = document.getElementById("invitation-search-result");
            container.innerHTML = "";
        characters.length == 0 ? $("#no-character-can-be-invited").show() : $("#no-character-can-be-invited").hide()

        for(let cIndex in characters){
            container.appendChild(createItem(characters[cIndex]));
        }

        function createItem(character){
            const container = document.createElement("DIV");
                container.classList.add("invitable-character");

                const nameContainer = document.createElement("DIV");
                    nameContainer.innerHTML = character.characterName;
            container.appendChild(nameContainer);

                const buttonWrapper = document.createElement("DIV");
                    buttonWrapper.classList.add("button-wrapper");

                    const wrapperSpan = document.createElement("SPAN");
                        const visitProfileButton = document.createElement("BUTTON");
                            visitProfileButton.innerHTML = Localization.getAdditionalContent("visit-profile");
                    wrapperSpan.appendChild(visitProfileButton);
                buttonWrapper.appendChild(wrapperSpan);

                    const inviteButton = document.createElement("BUTTON");
                        inviteButton.innerHTML = Localization.getAdditionalContent("invite");
                buttonWrapper.appendChild(inviteButton);
                $(buttonWrapper).hover(function(){$(wrapperSpan).fadeIn()}, function(){$(wrapperSpan).fadeOut()});
            container.appendChild(buttonWrapper);
            return container;
        }
    }
})();