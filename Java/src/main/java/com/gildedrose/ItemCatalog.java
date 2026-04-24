package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

// Small compromise: avoid duplicated magic strings without adding config or infrastructure.
final class ItemCatalog {
    static final String AGED_BRIE = "Aged Brie";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED_PREFIX = "Conjured";
    static final String DEXTERITY_VEST = "+5 Dexterity Vest";
    static final String ELIXIR_OF_THE_MONGOOSE = "Elixir of the Mongoose";
    static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";

    // Daily update rules:
    // regular items lose 1 quality point, twice after sell date
    // conjured items lose 2 quality points, twice after sell date
    // Aged Brie gains quality, twice after sell date
    // Sulfuras never changes
    // Backstage passes gain more quality as the concert approaches, then drop to 0

    private static final ItemUpdate REGULAR_UPDATE = new TemporalEvolution(item -> {
        decreaseQuality(item, 1);
        decreaseSellIn(item);

        if (isExpired(item)) {
            decreaseQuality(item, 1);
        }
    });

    private static final ItemUpdate CONJURED_UPDATE = new TemporalEvolution(item -> {
        decreaseQuality(item, 2);
        decreaseSellIn(item);

        if (isExpired(item)) {
            decreaseQuality(item, 2);
        }
    });

    private static final ItemUpdate AGED_BRIE_UPDATE = new TemporalEvolution(item -> {
        increaseQuality(item, 1);
        decreaseSellIn(item);

        if (isExpired(item)) {
            increaseQuality(item, 1);
        }
    });

    private static final ItemUpdate SULFURAS_UPDATE = new TemporalEvolution(item -> {
    });

    private static final ItemUpdate BACKSTAGE_PASS_UPDATE = new TemporalEvolution(item -> {
        increaseQuality(item, 1);

        if (item.sellIn < 11) {
            increaseQuality(item, 1);
        }

        if (item.sellIn < 6) {
            increaseQuality(item, 1);
        }

        decreaseSellIn(item);

        if (isExpired(item)) {
            item.quality = 0;
        }
    });

    // Maps known legacy item names to their update behavior.
    private static final Map<String, ItemUpdate> UPDATES = new HashMap<String, ItemUpdate>();

    static {
        UPDATES.put(DEXTERITY_VEST, REGULAR_UPDATE);
        UPDATES.put(ELIXIR_OF_THE_MONGOOSE, REGULAR_UPDATE);
        UPDATES.put(AGED_BRIE, AGED_BRIE_UPDATE);
        UPDATES.put(SULFURAS, SULFURAS_UPDATE);
        UPDATES.put(BACKSTAGE_PASS, BACKSTAGE_PASS_UPDATE);
        UPDATES.put(CONJURED_MANA_CAKE, CONJURED_UPDATE);
    }

    private ItemCatalog() {
    }

    // Resolves the behavior for an item.
    static ItemUpdate updateFor(Item item) {
        if (item.name != null && item.name.startsWith(CONJURED_PREFIX)) {
            return CONJURED_UPDATE;
        }

        return UPDATES.getOrDefault(item.name, REGULAR_UPDATE);
    }

    private static void decreaseQuality(Item item, int amount) {
        TemporalEvolution.decreaseQuality(item, amount);
    }

    private static void increaseQuality(Item item, int amount) {
        item.quality = Math.min(50, item.quality + amount);
    }

    private static void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private static boolean isExpired(Item item) {
        return item.sellIn < 0;
    }
}