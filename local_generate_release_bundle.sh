#!/bin/bash

# Read properties from local.properties
grep_prop() {
  grep "^$1=" local.properties | cut -d'=' -f2-
}

# Always read signing info
RELEASE_STORE_PASSWORD=$(grep_prop "RELEASE_STORE_PASSWORD")
RELEASE_KEY_ALIAS=$(grep_prop "RELEASE_KEY_ALIAS")
RELEASE_KEY_PASSWORD=$(grep_prop "RELEASE_KEY_PASSWORD")

# Check for keystore file
if [ ! -f release.keystore ]; then
  KEYSTORE_BASE64=$(grep_prop "KEYSTORE_BASE64")
  if [ -z "$KEYSTORE_BASE64" ]; then
    echo "Error: release.keystore does not exist and KEYSTORE_BASE64 is missing in local.properties."
    exit 1
  fi
  echo "$KEYSTORE_BASE64" | base64 --decode > release.keystore
fi

# Check required signing info
if [ -z "$RELEASE_STORE_PASSWORD" ] || [ -z "$RELEASE_KEY_ALIAS" ] || [ -z "$RELEASE_KEY_PASSWORD" ]; then
  echo "Error: One or more required signing properties are missing in local.properties."
  exit 1
fi

if [[ "$GITHUB_REF_TYPE" == "tag" ]]; then
  VERSION_REF="$GITHUB_REF_NAME"
else
  VERSION_REF="${GITHUB_SHA::7}"
fi

mkdir -p ~/.gradle
{
  echo "RELEASE_STORE_FILE=release.keystore"
  echo "RELEASE_STORE_PASSWORD=$RELEASE_STORE_PASSWORD"
  echo "RELEASE_KEY_ALIAS=$RELEASE_KEY_ALIAS"
  echo "RELEASE_KEY_PASSWORD=$RELEASE_KEY_PASSWORD"
  echo "VERSION_REF=$VERSION_REF"
} >> ~/.gradle/gradle.properties

./gradlew generateReleaseBundle --stacktrace
