#!/bin/bash
pwd
cd data-finder-web
flutter build web
cd ..
rm -rf web-assets/datafinder-web
mkdir -p web-assets/datafinder-web
cp -R  data-finder-web/build/web/ web-assets/datafinder-web
