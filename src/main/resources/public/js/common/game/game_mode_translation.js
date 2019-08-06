(function GameModeTranslation(){
    scriptLoader.loadScript("/js/common/localization/localization_map.js");
    scriptLoader.loadScript("/js/common/game/game_mode.js");

    const gameModeLocalization = localizationMapCache.getLocalizationMap("game_mode");

    window.gameModeTranslation = new function(){
        this.getDataName = function(gameMode){
            switch(gameMode){
                case GameMode.CLAN_WARS:
                    return gameModeLocalization.getLocalization("CLAN_WARS_DATA");
                break;
                case GameMode.TEAMFIGHT:
                    return gameModeLocalization.getLocalization("TEAMFIGHT_DATA");
                break;
                default:
                    throwException("IllegalArgument", gameMode + " has no data localization");
                break;
            }
        }

        this.translateData = function(gameMode, data){
            switch(gameMode){
                case GameMode.CLAN_WARS:
                    return gameModeLocalization.getLocalization(data);
                break;
                default:
                    return data;
                break;
            }
        }
    }
})();