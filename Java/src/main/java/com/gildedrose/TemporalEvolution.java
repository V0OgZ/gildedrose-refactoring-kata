package com.gildedrose;

// Wraps item updates driven by time.
// It will host shared operations used by temporal item evolutions.
class TemporalEvolution implements ItemUpdate {
    private final ItemUpdate update;

    // Accepts the concrete evolution as a function to avoid anonymous subclasses.
    TemporalEvolution(ItemUpdate update) {
        this.update = update;
    }

    @Override
    public void update(Item item) {
        update.update(item);
    }

    //Decrease by amount, cannot be negative.
    static void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }

    //Increase by amount, cannot be greater than 50.
    static void increaseQuality(Item item, int amount) {
        item.quality = Math.min(50, item.quality + amount);
    }

    static void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    static boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    static void resetQuality(Item item) {
        item.quality = 0;
    }
}