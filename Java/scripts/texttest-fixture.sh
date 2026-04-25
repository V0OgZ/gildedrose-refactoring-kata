#!/usr/bin/env bash
set -euo pipefail

EXPECTED="../texttests/ThirtyDays/stdout.gr"
ACTUAL="../texttests/ThirtyDays/stdout.actual.gr"

mvn -q test-compile >/dev/null
java -cp target/classes:target/test-classes com.gildedrose.TexttestFixture 30 > "$ACTUAL"

case "${1:-check}" in
  check)
    diff -u "$EXPECTED" "$ACTUAL"
    ;;
  update)
    cp "$ACTUAL" "$EXPECTED"
    ;;
  run)
    cat "$ACTUAL"
    ;;
  clean)
    rm -f "$ACTUAL"
    ;;
  *)
    echo "Usage: scripts/texttest-fixture.sh [check|update|run|clean]"
    exit 1
    ;;
esac