package com.gildedrose;

import static com.gildedrose.ItemCatalog.*;
import static com.gildedrose.TemporalEvolution.decreaseQuality;

public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        InventoryItem[] inventoryItems = new InventoryItem[] {
                new InventoryItem(DEXTERITY_VEST, 10, 20, new TemporalEvolution(item -> decreaseQuality(item, 1))),
                new InventoryItem(AGED_BRIE, 2, 0, new TemporalEvolution(item -> { })),
                new InventoryItem(ELIXIR_OF_THE_MONGOOSE, 5, 7, new TemporalEvolution(item -> decreaseQuality(item, 1))),
                new InventoryItem(SULFURAS, 0, 80, new TemporalEvolution(item -> { })),
                new InventoryItem(SULFURAS, -1, 80, new TemporalEvolution(item -> { })),
                new InventoryItem(BACKSTAGE_PASS, 15, 20),
                new InventoryItem(BACKSTAGE_PASS, 10, 49, new TemporalEvolution(item -> { })),
                new InventoryItem(BACKSTAGE_PASS, 5, 49, new TemporalEvolution(item -> { })),
                // this conjured item does not work properly yet
                new InventoryItem(CONJURED_MANA_CAKE, 3, 6, new TemporalEvolution(item -> decreaseQuality(item, 2)))
        };

        Item[] items = itemsFrom(inventoryItems);

        GildedRose app = new GildedRose(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

    private static Item[] itemsFrom(InventoryItem[] inventoryItems) {
        Item[] items = new Item[inventoryItems.length];

        for (int i = 0; i < inventoryItems.length; i++) {
            items[i] = inventoryItems[i].item;
        }

        return items;
    }
}