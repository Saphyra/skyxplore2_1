(function SetLanguageController(){
    events.SET_LANGUAGE = "SET_LANGUAGE";

    $(document).ready(loadLanguage);

    function loadLanguage(){
        const languages = loadLanguages();
        const selectedLanguage = getLocale();

        const languageSelectField = document.getElementById("language");

        for(let locale in languages){
            const displayLanguage = languages[locale];
            languageSelectField.appendChild(createOption(locale, displayLanguage, selectedLanguage));
        }

        function loadLanguages(){
            const response = dao.sendRequest(HttpMethod.GET, Mapping.GET_LANGUAGES);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.body);
            }else{
                throwException(response.toString(), "error", "InvalidApiResponse: ");
            }
        }

        function createOption(locale, displayLanguage, selectedLanguage){
            const optionField = document.createElement("OPTION");
                optionField.value = locale;
                optionField.innerHTML = displayLanguage;
                optionField.selected = locale === selectedLanguage;
            return optionField;
        }
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SET_LANGUAGE},
        function(){
            const selectedLanguage = document.getElementById("language").value;
            const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.SET_LOCALE, selectedLanguage));
                request.processValidResponse = function(){
                    sessionStorage.successMessage = "language-changed";
                    window.location.reload();
                }
            dao.sendRequestAsync(request);
        }
    ))
})();