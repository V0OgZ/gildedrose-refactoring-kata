package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    // Keep GildedRose as the legacy entry point.
    // Actual item behavior is resolved by the catalog.
    public void updateQuality() {
        for (Item item : items) {
            new InventoryItem(item, ItemCatalog.updateFor(item)).update();
        }
    }
}