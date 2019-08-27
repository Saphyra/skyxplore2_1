function loadLocalization(fileName, successCallback){
    const DEFAULT_LOCALE = "hu";

    createQuery(
        fileName,
        getLocale(),
        successCallback,
        createQuery(
            fileName,
            getBrowserLanguage(),
            successCallback,
            createQuery(
                fileName,
                DEFAULT_LOCALE,
                successCallback
            )
        )
    )();

    function createQuery(fileName, locale, successCallback, errorCallback){
        const request = new Request(HttpMethod.GET, getPath(locale, fileName));
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(localization){
                successCallback(localization);
            }
            if(errorCallback){
                request.processInvalidResponse = errorCallback;
            }

        return function(){
            dao.sendRequestAsync(request);
        }
    }

    function getPath(locale, fileName){
        return "/i18n/page/" + locale + "/" + fileName + ".json";
    }
}