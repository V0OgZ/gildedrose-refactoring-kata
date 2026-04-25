# Text fixture script

This folder contains one helper script for the Java text fixture:

```bash
scripts/testfixture.sh
```

Run all commands from the `Java/` folder.

## Commands

Check the current fixture output against the approved TextTest output:

```bash
scripts/testfixture.sh check
```

Update the approved TextTest output after an intentional behavior change:

```bash
scripts/testfixture.sh update
```

Print the current fixture output:

```bash
scripts/testfixture.sh run
```

Remove the generated local comparison file:

```bash
scripts/testfixture.sh clean
```

## Files

Approved output:

```text
../texttests/ThirtyDays/stdout.gr
```

Generated local output:

```text
../texttests/ThirtyDays/stdout.actual.gr
```

The generated local output is only used for comparison and should not be committed.

Use `update` only when the output change is intentional.