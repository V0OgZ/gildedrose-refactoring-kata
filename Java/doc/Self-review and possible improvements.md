# Design self-review and future improvements

After reviewing my solution, I think the overall direction is solid, but some parts could be made clearer.

One naming issue is the field used inside `TemporalEvolution`:

```java
private final ItemUpdate update;

@Override
public void update(Item item) {
    update.update(item);
}
```

This works, but it is not expressive enough. The word `update` is used both for the field and for the method, which makes the code harder to read than necessary.

A clearer version would be:

```java
private final ItemUpdate evolutionRule;

@Override
public void update(Item item) {
    evolutionRule.update(item);
}
```

or:

```java
private final ItemUpdate strategy;

@Override
public void update(Item item) {
    strategy.update(item);
}
```

This better expresses the intention: we are applying an evolution rule to an item.

Another possible improvement would be to separate the catalog from the rule definitions. At the moment, `ItemCatalog` is responsible for resolving item names and also contains the update rules. This is acceptable for the kata scope, but if the domain grows, I would split those responsibilities.

For example:

```text
ItemCatalog        -> legacy item names or categories
EvolutionRules     -> concrete update behaviors
ItemUpdateRegistry -> mapping between item type/category and rule
```

In a real system, I would probably avoid mapping raw item names directly to rules. Item names would likely come from a database, and each item would belong to a business category such as:

```text
CHEESE
LEGENDARY
EVENT_TICKET
CONJURED
REGULAR
```

Then the system would map categories to evolution rules instead of relying on hardcoded item names.

This would make the design more robust and easier to extend.

Overall, the solution is close to what I wanted: item data is separated from item behavior, and new rules can be added without modifying a large conditional block. The main improvements I would make are clearer naming and a cleaner separation between catalog data, rule resolution, and rule implementation.

Since this was my first kata, I was honestly not always sure where to stop. I wanted to prepare the design for future rules, but I also had to avoid over-engineering a small exercise.