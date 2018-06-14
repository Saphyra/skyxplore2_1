(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/shop/menu_controller.js");
        scriptLoader.loadScript("js/shop/content_controller.js");
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        
        this.displayMoney = displayMoney;
        
        $(document).ready(function(){
            displayMoney();
        });
    }
    
    /*
    Displays the money of the user.
    */
    function displayMoney(){
        try{
            const moneyField = document.getElementById("money");
            moneyField.innerHTML = characterDao.getMoney(sessionStorage.characterId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();