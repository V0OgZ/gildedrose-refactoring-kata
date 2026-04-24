package com.gildedrose;

// Associates a legacy Item with the update function 
final class InventoryItem {
    final Item item;
    final ItemUpdate update;

    InventoryItem(Item item, ItemUpdate update) {
        this.item = item;
        this.update = update;
    }

    void update() {
        update.update(item);
    }
}