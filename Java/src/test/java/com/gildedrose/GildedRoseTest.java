package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.gildedrose.ItemCatalog.DEXTERITY_VEST;
import static com.gildedrose.TemporalEvolution.decreaseQuality;

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

}
