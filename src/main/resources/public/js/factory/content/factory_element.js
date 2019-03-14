scriptLoader.loadScript("js/common/localization/date_time_formatter.js");

function FactoryElement(
    id,
    rm,
    me,
    cl,
    ml,
    ctl,
    ai,
    bb
){
    let amount = 1;
    const itemId = id;
    const requiredMaterials = rm;
    const materialElements = me;
    const costLabel = cl;
    const moneyLabel = ml;
    const constructionTimeLabel = ctl;
    const amountInput = ai;
    const buildButton = bb;
    const itemData = itemCache.get(itemId);

    addListeners();
    amountChanged();

    function amountChanged(){
        updateMaterialElements();

        costLabel.innerHTML = itemData.buildprice * amount;
        moneyLabel.innerHTML = moneyController.getMoney();
        constructionTimeLabel.innerHTML = dateTimeFormatter.convertTimeStamp(itemData.constructiontime * amount);

        function updateMaterialElements(){
            for(let mIndex in materialElements){
                const materialElement = materialElements[mIndex];

                materialElement.updateRequiredAmount(amount, requiredMaterials[materialElement.getId()])
            }
        }
    }

    function addListeners(){
        buildButton.onclick = function(){
            eventProcessor.processEvent(new Event(
                events.ADD_TO_QUEUE,
                {itemId: itemId, amount: amount}
            ));
        }

        $(amountInput).on("change keyup", function(){
            amount = Number(amountInput.value);
            amountChanged();
        });
    }
}

function FactoryElementBuilder(id){
    const itemId = id;
    let requiredMaterials = {};
    const materialElements = [];
    let costLabel = document.createElement("SPAN");
    let moneyLabel = document.createElement("SPAN");
    let constructionTimeLabel = document.createElement("SPAN");
    let amountInput = document.createElement("INPUT");
    let buildButton = document.createElement("BUTTON");

    this.requiredMaterials = function(rm){
        requiredMaterials = rm;
    }

    this.addMaterialElement = function(element){
        materialElements.push(element);
    }

    this.costLabel = function(label){
        costLabel = label;
    }

    this.moneyLabel = function(label){
        moneyLabel = label;
    }

    this.constructionTimeLabel = function(label){
        constructionTimeLabel = label;
    }

    this.amountInput = function(input){
        amountInput = input;
    }

    this.buildButton = function(button){
        buildButton = button;
    }

    this.build = function(){
        return new FactoryElement(
            itemId,
            requiredMaterials,
            materialElements,
            costLabel,
            moneyLabel,
            constructionTimeLabel,
            amountInput,
            buildButton
        );
    }
}

function MaterialElement(id, requiredAmount, storedAmount){
    const materialId = id;
    const requiredAmountLabel = requiredAmount;
    const storedAmountLabel = storedAmount;

    updateRequiredAmount();

    this.updateRequiredAmount = function(amount, requiredAmount){
        requiredAmountLabel.innerHTML = amount * requiredAmount;
    }

    this.updateStoredAmount = updateRequiredAmount;

    function updateRequiredAmount(){
        storedAmountLabel.innerHTML = materialsController.getMaterialAmount(materialId);
    }

    this.getId = function(){
        return materialId;
    }
}