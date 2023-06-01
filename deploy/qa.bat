@echo off

set MANIFEST_TARGET=qa-gcp
call .\gradlew clean build cfManifest -x test
cf push -f manifest-generated.yml
set MANIFEST_TARGET=