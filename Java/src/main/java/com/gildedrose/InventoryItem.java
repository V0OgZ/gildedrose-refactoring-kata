package com.gildedrose;

// Associates a legacy Item with the update function.
final class InventoryItem {
    final Item item;
    final ItemUpdate update;

    InventoryItem(String name, int sellIn, int quality) {
        this(new Item(name, sellIn, quality));
    }

    InventoryItem(String name, int sellIn, int quality, ItemUpdate update) {
        this(new Item(name, sellIn, quality), update);
    }

    InventoryItem(Item item) {
        this(item, itemToUpdate -> {});
    }

    InventoryItem(Item item, ItemUpdate update) {
        this.item = item;
        this.update = update;
    }

    void update() {
        update.update(item);
    }
}