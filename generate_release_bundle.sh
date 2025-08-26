#!/bin/bash
set -e

chmod +x ./gradlew

echo "$KEYSTORE_BASE64" | base64 --decode > release.keystore

if [[ "$GITHUB_REF_TYPE" == "tag" ]]; then
  VERSION_REF="$GITHUB_REF_NAME"
else
  VERSION_REF="${GITHUB_SHA::7}"
fi

mkdir -p ~/.gradle
{
  echo "RELEASE_STORE_FILE=release.keystore"
  echo "RELEASE_STORE_PASSWORD=$RELEASE_STORE_PASSWORD"
  echo "RELEASE_KEY_ALIAS=$KEY_ALIAS"
  echo "RELEASE_KEY_PASSWORD=$RELEASE_KEY_PASSWORD"
  echo "VERSION_REF=$VERSION_REF"
} >> ~/.gradle/gradle.properties

./gradlew generateReleaseBundle --stacktrace
