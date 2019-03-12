(function MoneyController(){
    events.MONEY_CHANGED = "money_changed";

    let money = null;

    window.moneyController = new function(){
        this.getMoney = getMoney;
    }

    $(document).ready(function(){
        loadMoney();
    });

    function getMoney(){
        return money;
    }

    function loadMoney(){
        const request = new Request(HttpMethod.GET, Mapping.CHARACTER_MONEY);
            request.convertResponse = function(response){
                return Number(response.body);
            }
            request.processValidResponse = function(characterMoney){
                money = characterMoney;
                sendMoneyChangedEvent();
            }

        dao.sendRequestAsync(request);
    }

    function sendMoneyChangedEvent(){
        eventProcessor.processEvent(new Event(
            events.MONEY_CHANGED,
            {money: money}
        ));
    }
})();