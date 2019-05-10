function LocalizationMap(fileName){
    let map = null;

    loadLocalization(fileName, function(localization){map = localization});

    this.getLocalization = function(key){
        return map[key] || throwException("IllegalArgument", "localization not found with key " + key);
    }
}