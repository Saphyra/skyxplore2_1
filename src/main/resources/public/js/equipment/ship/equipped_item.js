function EquippedItem(container, item, elem){
    const containerId = container;
    const itemId = item;
    const itemContainerElement = elem;

    this.getId = function(){
        return itemId;
    }

    this.getContainerId = function(){
        return containerId;
    }

    this.getElement = function(){
        return itemContainerElement;
    }
}