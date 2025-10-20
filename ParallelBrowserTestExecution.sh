#!/bin/bash

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     MULTI-BROWSER PARALLEL TEST EXECUTION                  ║"
echo "╚════════════════════════════════════════════════════════════╝"


# Chạy Maven với profile parallel-browsers
mvn clean test -P parallel-browsers

