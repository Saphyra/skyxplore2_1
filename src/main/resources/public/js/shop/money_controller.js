(function MoneyControlelr(){
    events.MONEY_CHANGED = "money_changed";
    
    let money = null;
    let cartCost = 0;
    
    window.moneyController = new function(){
        this.getUsableBalance = function(){return money - cartCost};
    }
    
    $(document).ready(function(){
        loadMoney();
    });
    
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
            {money: money, cartCost: cartCost, usableBalance: money - cartCost}
        ));
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MONEY_CHANGED},
        function(event){
            const payload = event.getPayload();
            $("#money").text(payload.money);
            $("#cart-cost").text(payload.cartCost);
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ADD_TO_CART},
        function(event){
            cartCost += itemCache.get(event.getPayload()).buyprice;
            sendMoneyChangedEvent();
        }
    ));
})();