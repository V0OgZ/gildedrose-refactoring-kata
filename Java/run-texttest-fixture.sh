#!/usr/bin/env bash
set -e

mvn test-compile exec:java \
  -Dexec.mainClass="com.gildedrose.TexttestFixture" \
  -Dexec.classpathScope=test \
  -Dexec.args="$*"