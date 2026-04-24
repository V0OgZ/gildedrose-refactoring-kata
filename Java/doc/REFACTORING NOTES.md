# Refactoring notes

## 1. Principles used

I used KISS and SOLID as practical tools, not as dogma.

My goal was not to add patterns everywhere, but to make the code easier to understand, safer to change, and easier to test.

I avoided external infrastructure such as JSON configuration or a database because that would have moved the problem elsewhere instead of solving the kata.

I kept the business tests on the public kata contract: create `Item` objects, call `GildedRose.updateQuality()`, and verify the resulting state.

## 2. Refactoring decisions and order

I started by centralizing the legacy item names in `ItemCatalog`. The original code had duplicated magic strings everywhere, and I did not want to add more business logic on top of that.

Then I reused the catalog inside the text fixture, because the fixture also contained the same legacy strings. At that point, the goal was still not to change behavior, only to remove noise and duplication.

After that, I replaced the placeholder test with a real business test. I wanted the tests to describe the observable behavior, not the implementation details.

Once the basic safety net was there, I introduced `ItemUpdate`, then `TemporalEvolution`, and later `InventoryItem`. The reason was the main constraint of the kata: `Item` cannot be changed. In a cleaner object model, the item itself would probably carry its evolution behavior. Since that was forbidden, I associated behavior externally.

I first experimented with an abstract temporal evolution, then moved to a lambda-based approach because it was lighter and more readable. The idea was to keep the behavior flexible without creating a class hierarchy too early.

The text fixture was then updated to show how an inventory item can be initialized with an associated update behavior. This was not meant to become the main business test contract; it was a characterization/demo tool.

Only after the names were centralized, the tests were in place, and the update behavior model existed, I attacked the main `GildedRose` logic. The large legacy method was replaced by a simpler flow where `GildedRose` keeps the public entry point, while `ItemCatalog` resolves the correct update behavior.

Finally, I added tests for `Conjured` items and moved temporal operations such as quality changes and sell-in changes out of `ItemCatalog` into `TemporalEvolution`, so the catalog stays focused on mapping item names to behavior.

## 3. State of mind during the refactor

My first reaction when I saw the original `updateQuality()` method was honestly a small scream of horror.

The code was not just ugly; it was dangerous to extend. It mixed item detection, quality rules, sell-in rules, expiration logic, special cases, and string comparisons in one place.

I deliberately did not start by touching the core business logic. I did the opposite: first I removed the obvious mess, then I built a small toolbox, and only then I attacked the final boss.

The order was intentional:

```text
clean the magic strings
add real tests
introduce the update behavior model
prepare the fixture
then refactor the business core
then add Conjured