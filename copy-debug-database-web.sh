#!/bin/bash
pwd
rm -rf web-assets/datafinder-web/debug-database/
mkdir -p web-assets/datafinder-web/debug-database/
cp -R  Android-Debug-Database/debug-db-base/src/main/assets/ web-assets/datafinder-web/debug-database/
