#!/bin/bash

echo "Building BensDeals Application"

ant clean config test release reset

echo "Creating release directory : ant_build/release"

mkdir ant_build/release

echo "Copying release APK to release directory"

cp ant_build/*release.apk ant_build/release/

echo "All set." 