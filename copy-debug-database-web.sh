#!/bin/bash
pwd
rm -rf web-assets/debug-database-web/
mkdir -p web-assets/debug-database-web/
cp -R  Android-Debug-Database/debug-db-base/src/main/assets/ web-assets/debug-database-web/
