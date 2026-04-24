package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.gildedrose.ItemCatalog.DEXTERITY_VEST;
import static com.gildedrose.TemporalEvolution.decreaseQuality;
import static com.gildedrose.ItemCatalog.CONJURED_MANA_CAKE;

class GildedRoseTest {

    @Test
    void dexterityVestLosesQuality() {
        Item[] items = new Item[] { new Item(DEXTERITY_VEST, 10, 20) };
    
        GildedRose app = new GildedRose(items);
        app.updateQuality();
    
        assertEquals(DEXTERITY_VEST, app.items[0].name);
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }


// InventoryItem is the bridge between the legacy Item and its update behavior.
@Test
void inventoryItemAppliesAssociatedUpdate() {
    InventoryItem inventoryItem = new InventoryItem(
            new Item(DEXTERITY_VEST, 10, 20),
            new TemporalEvolution(item -> decreaseQuality(item, 1))
    );

    inventoryItem.update();

    assertEquals(19, inventoryItem.item.quality);
}

@Test
void conjuredManaCakeLosesQualityTwiceAsFast() {
    Item[] items = new Item[] { new Item(CONJURED_MANA_CAKE, 3, 6) };

    GildedRose app = new GildedRose(items);
    app.updateQuality();

    assertEquals(CONJURED_MANA_CAKE, app.items[0].name);
    assertEquals(2, app.items[0].sellIn);
    assertEquals(4, app.items[0].quality);
}

@Test
void conjuredManaCakeLosesQualityTwiceAsFastAfterSellDate() {
    Item[] items = new Item[] { new Item(CONJURED_MANA_CAKE, 0, 6) };

    GildedRose app = new GildedRose(items);
    app.updateQuality();

    assertEquals(CONJURED_MANA_CAKE, app.items[0].name);
    assertEquals(-1, app.items[0].sellIn);
    assertEquals(2, app.items[0].quality);
}

@Test
void conjuredManaCakeQualityNeverGoesNegative() {
    Item[] items = new Item[] { new Item(CONJURED_MANA_CAKE, 3, 1) };

    GildedRose app = new GildedRose(items);
    app.updateQuality();

    assertEquals(CONJURED_MANA_CAKE, app.items[0].name);
    assertEquals(2, app.items[0].sellIn);
    assertEquals(0, app.items[0].quality);
}
// Tests stay on the public kata contract: Item[] + GildedRose.updateQuality().
// InventoryItem is an implementation detail, so business tests should not depend on it.
}
