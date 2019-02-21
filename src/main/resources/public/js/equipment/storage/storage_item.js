function StorageItem(id, parent, container, amount){
    const itemId = id;
    const categoryList = parent;
    const itemContainer = container;
    const amountElement = amount;
    let currentAmount = 0;

    parent.appendChild(container);

    this.getId = function(){
        return itemId;
    }

    this.getContainer = function(){
        return itemContainer;
    }

    this.getAmount = function(){
        return currentAmount;
    }

    this.increaseAmount = function(){
        currentAmount++;
        amountElement.innerHTML = currentAmount;
    }

    this.decreaseAmount = function(){
        currentAmount--;
        amountElement.innerHTML = currentAmount;
    }
}