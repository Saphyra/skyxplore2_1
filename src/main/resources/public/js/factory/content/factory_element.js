scriptLoader.loadScript("/js/common/localization/date_time_formatter.js");
scriptLoader.loadScript("/js/common/equipment/item_cache.js");

function FactoryElement(
    id,
    reqMat,
    matEls,
    costLab,
    monLab,
    constTimeLab,
    amInp,
    buyBut
){
    let buildable = true;
    let amount = 1;
    const itemId = id;
    const requiredMaterials = reqMat;
    const materialElements = matEls;
    const costLabel = costLab;
    const moneyLabel = monLab;
    const constructionTimeLabel = constTimeLab;
    const amountInput = amInp;
    const buildButton = buyBut;
    const itemData = itemCache.get(itemId);

    addListeners();
    amountChanged();

    function amountChanged(){
        buildable = true;
        updateMaterialElements();
        updateCost();

        buildButton.disabled = !buildable;
        constructionTimeLabel.innerHTML = dateTimeFormatter.convertTimeStamp(itemData.constructiontime * amount);

        function updateCost(){
            const cost = (itemData.buildprice || 0) * amount;
            const money = moneyController.getMoney();
            costLabel.innerHTML = cost;
            moneyLabel.innerHTML = money;

            displayBuildStatus(costLabel.parentNode, cost <= money, setBuildable);
        }

        function updateMaterialElements(){
            for(let mIndex in materialElements){
                const materialElement = materialElements[mIndex];

                materialElement.updateRequiredAmount(amount, requiredMaterials[materialElement.getId()], setBuildable);
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

    function setBuildable(b){
        buildable = b;
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

    updateStoredAmount();

    this.updateRequiredAmount = function(amount, requiredAmount, errorCallBack){
        const amountCost = amount * requiredAmount
        requiredAmountLabel.innerHTML = amountCost;

        displayBuildStatus(requiredAmountLabel.parentNode, amountCost <= materialsController.getMaterialAmount(materialId), errorCallBack);
    }

    this.updateStoredAmount = updateStoredAmount;

    function updateStoredAmount(){
        storedAmountLabel.innerHTML = materialsController.getMaterialAmount(materialId);
    }

    this.getId = function(){
        return materialId;
    }
}

function displayBuildStatus(node, buildable, errorCallBack){
    if(buildable){
        node.classList.remove("not-buildable");
        node.classList.add("buildable");
    }else{
        node.classList.remove("buildable");
        node.classList.add("not-buildable");
        errorCallBack(buildable);
    }
}