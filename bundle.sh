#!/bin/bash
set -e

./gradlew clean build
FILES="$(ls mod/*/build/libs/TouchController-*-*.jar mod/*/*/build/libs/TouchController-*-*.jar | grep -v "slim\|noreobf\|fat")"
MOD_VERSION="$(grep modVersion gradle.properties | cut -d= -f2)"
OUTPUT_FILE="bundle/TouchController-${MOD_VERSION}.zip"
rm -f "${OUTPUT_FILE}"
zip -j "${OUTPUT_FILE}" ${FILES}
