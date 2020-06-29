#!/bin/bash
rootDir=`pwd`
echo '工作路径：'${rootDir}
bintrayKey=`cat local.properties |grep 'bintrayKey' | awk -F '='   '{print $2}'`
bintrayUser=`cat local.properties |grep 'bintrayUser' | awk -F '='   '{print $2}'`
libVersion=`cat gradle.properties |grep 'maven_datafinder_lib_version' | awk -F '='   '{print $2}'`
if [[ -z ${bintrayKey} ]]; then
    echo "bintrayKey 不得为空！"
    exit 1
fi
if [[ -z ${bintrayUser} ]]; then
    echo "bintrayUser 不得为空！"
    exit 1
fi
if [[ "$1" = 'release' ]]; then
    echo '启用release版本'
else
    if [[ -f '.version' ]]; then
        subVersion=`cat .version  | awk -F '|'   '{print $2}'`
        mainVersion=`cat .version  | awk -F '|'   '{print $1}'`
        if [[ "$mainVersion" = "$libVersion" ]]; then
            subVersion=$(($subVersion+1))
        else
            subVersion=1
        fi
    else
        subVersion=1
    fi
    echo "$libVersion|${subVersion}" > .version
    libVersion=${libVersion}'-'${subVersion}'-SNAPSHOT'
fi
echo '构建版本:'${libVersion}
echo 'bintrayKey='${bintrayKey}
echo 'bintrayUser='${bintrayUser}
echo '清理flutter web'
cd ${rootDir}'/data-finder-web'
flutter clean
echo '开始打包sdk'
cd ${rootDir}
rm -rf web-assets
./gradlew clean build bintrayUpload -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} -PdryRun=false -PpublishVersion=${libVersion}
echo '发布成功，版本号：'${libVersion}