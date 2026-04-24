package com.gildedrose;

// Base class for item updates driven by time.
// It will host shared operations used by temporal item evolutions.
abstract class TemporalEvolution implements ItemUpdate {

    //Decrease by amount, cannot be negative.
	protected void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }
}