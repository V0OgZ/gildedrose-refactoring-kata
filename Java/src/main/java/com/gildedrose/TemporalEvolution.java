package com.gildedrose;

// Base class for item updates driven by time.
// It will host shared operations used by temporal item evolutions.
class TemporalEvolution implements ItemUpdate {
    private final ItemUpdate update;

    TemporalEvolution(ItemUpdate update) {
        this.update = update;
    }

	// Accepts the concrete evolution as a function to avoid anonymous subclasses.
    @Override
    public void update(Item item) {
        update.update(item);
    }

    //Decrease by amount, cannot be negative.
    static void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }
}