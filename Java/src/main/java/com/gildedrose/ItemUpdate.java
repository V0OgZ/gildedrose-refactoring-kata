package com.gildedrose;

// Defines a small function contract for updating an Item.
// This keeps update behavior separate from Item, which must stay unchanged.
@FunctionalInterface
interface ItemUpdate {
    void update(Item item);
}