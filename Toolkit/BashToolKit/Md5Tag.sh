#!/usr/bin/env bash

#
# File: Md5Tag.sh
# Description: Tag a file with its md5 sum
# Author: Wang Qian
# Create: 2016-05-06
# Last Modified: 2016-08-18
#

if [ $# -lt 1 ]; then
	echo "usage: ./Md5Tag.sh FILE"
	echo ""
	exit
fi

filename="$1"

tag=`md5sum $filename`
tag=${tag:0:8}

reversed=`echo $filename | rev`
dot=`expr index "$reversed" "."`
len=`expr length "$filename"`
tmp=`expr $len - $dot`
base=${filename:0:tmp}
suffix=${filename:tmp:$dot}

tagged=$base-$tag$suffix

cp $filename $tagged
