# Engineering Rules

Practical team working agreement.

This document defines engineering rules for a software team. It is not a theoretical checklist and it is not a catalogue of patterns. Its purpose is to help developers make decisions, review changes, maintain the system together, and keep the code safe to change.

The goal is simple: every change should make the system easier to understand, safer to modify, or better aligned with the business truth.

We want simple code, but not simplistic code. We avoid over-engineering, but we also avoid quick patches that make an existing problem worse.

## 1. Team Culture, Ownership, and Responsibility

### 1.1 Code belongs to the team

Code belongs to the team, not to one person.

Every change must be understandable, reviewable, testable, and maintainable by someone other than its author.

We do not try to impress each other with complicated code. We try to leave the system clearer, safer, and more maintainable than before.

A good change does not merely work locally. It can be explained to the team, discussed in review, tested, picked up later, and modified without fear.

### 1.2 The developer owns the change

A developer is responsible for what they commit.

This remains true when the code was written with help from a tool, an agent, or AI.

Before validating a change, the developer must be able to explain:

- why the change exists;
- which behavior it adds, modifies, or protects;
- which tests cover it;
- which trade-offs were made;
- why the solution is proportionate to the problem.

We do not commit code we do not understand.

### 1.3 Write for the next reader

Code should be written to be read.

If a decision is not obvious, it should be clarified through naming, code structure, a test, or a short comment.

The goal is not that the author can recover their intent three weeks later. The goal is that another developer can understand that intent without rebuilding the whole context.

### 1.4 Change the system carefully

Every change should reduce risk or answer a clear need.

Adding code to a fragile area without understanding what already exists can make the system worse.

Before adding a feature, it may be necessary to secure the area first:

- name unclear concepts;
- centralize business data;
- add a characterization test;
- reduce duplication;
- make behavior observable;
- separate responsibilities.

This is not cosmetic cleanup. It is preparation that makes the requested change safer.

### 1.5 Small coherent changes beat heroic changes

A change should have one clear reason to exist.

Small, coherent changes are easier to review, easier to test, easier to revert, and easier to understand later.

If a task naturally contains several independent subjects, split it:

- behavior change;
- refactoring;
- test coverage;
- documentation;
- migration;
- dependency update.

Do not hide unrelated work inside a large diff.

## 2. Business Truth, Model, and Design

### 2.1 Identify the source of truth

Before changing behavior, identify where the truth lives.

The truth may be:

- a business rule / product decision
- a specification or acceptance test
- an API contract


We do not guess business behavior from suspicious implementation details.

If the rule is unclear, clarify it before implementing it.

### 2.2 Technical layers must not invent business rules

A UI, screen, controller, API must not invent business rules.
They must not become a second source of truth.

### 2.3 Make important business data explicit

Important business data should be visible.

Avoid:

- scattered magic strings
- duplicated categories
- repeated business constants
- implicit rules buried in obscure conditions.

Depending on context, this data may live in:

- a catalog
- a registry
- a table
- validated configuration

The goal is not to make everything configurable
It is to is to avoid hiding business data in random code.

### 2.4 Use data-driven design when it clarifies the system

Here, data-driven means one precise thing: when behavior varies according to known business data, make that data explicit and use it to select the named rule or behavior.


A factory must not guess the business. It should select behavior from explicit, known business data.
A resolver must not hide unknown data. It should either find a known rule or clearly report that the data is not recognized.
Do not force a data-driven approach when the rule does not naturally fit data. In that case, write a clear, named, tested business rule.

Data-driven does not mean:

- putting everything in configuration;
- replacing all business logic with tables;
- hiding rules in files that are hard to validate;
- accepting unknown data with a silent default.

### 2.5 KISS first

Apply KISS first.

Prefer the simplest solution that satisfies the expected behavior and does not make the next change more dangerous.

Simple does not mean simplistic.

A quick patch that makes a fragile area worse is not a good solution.

### 2.6 Use SOLID pragmatically

* S — Single Responsibility Principle (SRP)
    A class should have one job only.
* O — Open/Closed Principle (OCP)
    Code should be open for extension, closed for modification.
* L — Liskov Substitution Principle (LSP)
    Subtypes should be replaceable for their base types without breaking behavior.
* I — Interface Segregation Principle (ISP)
    Don’t force classes to implement methods they don’t need.
* D — Dependency Inversion Principle (DIP)
    Depend on abstractions, not concrete implementations.

| Principle | Simple idea | Everyday analogy | What to avoid |
|----------|------------|----------------|---------------|
| **S (SRP)** | One class = one responsibility | A chef cooks, a waiter serves | “God classes” doing everything |
| **O (OCP)** | Add new behavior without changing existing code | Adding a new payment method without rewriting checkout | Editing stable code every time you add a feature |
| **L (LSP)** | Subclasses behave like their parent | Any bird class should act like a bird (no surprises) | Subclasses breaking expected behavior |
| **I (ISP)** | Small, focused interfaces | A remote with only needed buttons | Huge interfaces with unused methods |
| **D (DIP)** | Depend on interfaces, not concrete classes | Plugging devices into a universal socket | Hardcoding dependencies |


SOLID is a tool, not a religion.

An interface, abstraction, or pattern must answer a real problem.

An abstraction is justified when it:

- clarifies a rule;
- isolates behavior that truly varies;
- prevents a central method from becoming a do-it-all
- makes the code easier to test
- lets the system evolve without breaking existing behavior.


### 2.7 Prefer composition over reflex inheritance

Do not use inheritance by reflex.

If the model leads to:

- one class per business data value;
- one class per product;
- a hierarchy full of exceptions;
- subclasses that disable parent behavior;
- behavior forced into an artificial parent-child relationship;

then inheritance is probably the wrong axis.

We often prefer:

```text
explicit data + associated behavior
```

over an artificial hierarchy.

### 2.8 Separate decision from mechanics

There are often two different questions:

```text
which rule applies?
```

and:

```text
how does this rule change the state?
```

These responsibilities do not always belong in the same place.

The code that chooses a rule should not be buried in:

- mutation / parsing /formatting / persistence / logging / display errors

This separation makes the code easier to read, easier to test, and easier to evolve.

### 2.9 Naming is design

Naming is part of design.

A name should explain the intent of a concept. It should not only describe a technical shape.

Avoid vague names such as:

- `Manager`
- `Processor`
- `Handler`
- `Helper`
- `Util`
- `Thing`
- `Data`

unless they are genuinely the best choice in context.

If a concept is hard to name, slow down. It is often a sign that the model is not clear enough yet.

### 2.10 Comments should explain decisions

A comment should explain a decision, constraint, trade-off, or intent that is not obvious from the code.

do not re explain the code algo in the comment the code should be self explanatory in most of the cases

Prefer improving a name or structure over adding a useless comment.

When reading business code, we should see the business. When going into implementation details, we should see the mechanics.

If a method mixes several levels of reading, it becomes hard to maintain.

### 2.11 Public contracts are deliberate

A public contract is anything another part of the system depends on:

- API
- database schema
- event format
- test fixture contract

Changing a public contract must be intentional.

If a contract changes, the change should be named, tested, documented when useful, and reviewed as a behavior change.

Do not smuggle a contract change inside a refactor.

## 3. Tests, Errors, and Robustness

### 3.1 Tests are a contract

Tests should validate expected behavior, not only the current implementation.

A good business test describes:

- given a state;
- when an action is executed;
- then the observable result is correct.

Business tests should stay close to the public contract of the system.

They should not depend unnecessarily on internal details. Otherwise, every refactor breaks the tests even when behavior has not changed.

A useful test helps the team trust an important behavior.

### 3.2 Do not weaken tests to get green

Do not delete a test just to get green.

Do not skip a test to hide a problem.

Do not change an assertion without understanding the expected behavior.

When a test fails, look for the cause. Do NOT patch the symptom.

### 3.3 Legacy code needs characterization

On legacy code, secure behavior before refactoring.

Start with:

- characterization tests;
- targeted business tests;
- regression tests.

The goal is to change structure without losing expected behavior.

### 3.4 Approved outputs must be understood

When an approved output exists, it represents expected behavior.

If the change is only a refactor, the approved output should not change.

If a feature intentionally changes behavior, update the approved output only after reviewing the diff.

The diff must be understood before it is accepted.

### 3.5 Validate at boundaries test special cases

Avoid defensive programming everywhere.

The code should not be filled with silent guardrails that hide real problems.

Validate :

- user input
- external APIs
- files
- messages
- configuration
- databases

Inside the domain, code should be able to trust its contracts.

### 3.6 Fail fast, not crash fast

Principle:

```text
fail fast, not crash fast
```

If required data is missing, if a rule is unknown, if a contract is broken, or if a state is invalid, the system should report it clearly.

But it should not fail with:

- an obscure crash;
- an unreadable null pointer;
- a brutal panic;
- an assertion as the only runtime strategy;
- a silent repair.

An error should be explicit, readable, and actionable.

It should help the team understand what is wrong, where the problem is, and what must be fixed.

### 3.7 Fallbacks are rules NOT repair of broken inputs or data

A fallback is not a design strategy.

A fallback is a behavior rule.

It must be intentional, named, documented, and tested.

A fallback must not hide:

- missing data;
- an unclear rule;
- a broken contract;
- a misunderstood model.

Acceptable example:

```text
if no known special case applies, use the standard behavior
```

when the standard behavior is a real, named, tested business rule.

Unacceptable example:

```text
if the data is unknown, silently use a default and continue
```

In that case, the system looks robust, but it is probably hiding an error.

### 3.8 Important failures should be observable

Important failures should leave enough information to diagnose the problem.

Errors, logs, metrics, and diagnostics should help answer:

Observability must not become noise.

of course : Do NOT log secrets, credentials, or unnecessary personal data.


## 4. Repository, Review, Delivery, and AI

### 4.1 The repository is a shared workspace

The repository is a shared workspace.

Do not delete a file only because it looks old, temporary, or useless.

Understand its role first.

Avoid committing:

- personal IDE configuration;
- caches;
- logs;
- generated files that should not be versioned;
- machine-specific state;
- temporary artifacts.

### 4.2 The repository should make state clear

The repository should make it clear:

- what is source of truth
- what is generated
- what is obsolete
- what is active


### 4.3 Generated code and generated artifacts need a source

Generated files must have an identifiable source.

The team should know:

- what generated the file;
- how to regenerate it;
- whether it is committed intentionally;
- which source should be modified instead of editing the generated output.

Do not manually patch generated output


### 4.4 Code review is engineering work

Review is not a formality.

Review should check:

- correctness;
- business behavior;
- tests;
- readability;
- scope;
- error handling;
- public contracts;
- maintainability.

The reviewer should be direct and constructive.

The author should make the change easy to review by keeping the diff focused, explaining intent, and answering questions without defensiveness.

A review comment should improve the code, the shared understanding, or the safety of the change.

### 4.5 Commits should say what changed

A commit should have a clear intention.

Good examples:

- `refactor: centralize legacy item names`
- `test: cover conjured item behavior`
- `refactor: move temporal operations out of catalog`
- `docs: add engineering rules`

Bad examples:

- `misc`
- `fix stuff`
- `updates`
- `wip`
- `cleanup`
- `remaining changes`

If a commit mixes several independent subjects, split it.

Before committing, review the diff.

Avoid committing noise, local files, or unrelated changes by accident.

### 4.6 Git history should tell the story

A good Git history explains why the code evolved, not only that it changed.

Do not rewrite shared history without a clear reason and explicit agreement.

Commits should help review and maintenance. They should not be only local save points.

### 4.7 Documentation should follow reality

Documentation is useful when it helps the team make correct decisions.

Update documentation when a change affects:

- public behavior;
- setup instructions;
- architecture decisions;
- operational procedures;
- source-of-truth locations;
- known limitations.

Do not create documentation that is is not maintained.


### 4.9 AI usage

AI can help:

- code;
- review;
- refactor;
- generate tests;
- explore alternatives;
- challenge a design.

But responsibility remains human.

Before validating a change made with AI assistance, the developer must:

- review the diff;
- understand the produced code;
- run the relevant tests;
- understand the trade-offs;
- verify that the result respects the business rules;
- be able to explain the decision.

AI does not replace ownership. !!

An agent or AI tool must not turn a small request into a global refactor.

Keep the scope tight.


### 4.10 Definition of Done

A task is done when:

- the requested behavior works;
- the public contract is preserved or explicitly changed;
- the important business cases are tested;
- errors are not hidden by silent fallbacks;
- the code is more readable, safer, or more maintainable than before;
- the diff has been reviewed by the author;
- the relevant tests have been run;
- the commit explains the change clearly;
- the team can maintain the result.

The final question is not only:

```text
does it compile?
```

The real question is:

```text
can the next developer understand and change this without fear?
```
