#!/bin/bash
pwd
cd data-finder-web
flutter build web
cd ..
rm -rf web-assets/datafinder-web/datafinder
mkdir -p web-assets/datafinder-web/datafinder
cp -R  data-finder-web/build/web/ web-assets/datafinder-web/datafinder
# 打包zip
cd  web-assets/datafinder-web
rm -rf web-static.zip
zip -r web-static.zip datafinder debug-database