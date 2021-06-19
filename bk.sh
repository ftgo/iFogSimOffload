#!/bin/bash

out=out_`date +%Y%m%d%H%M%S`

mkdir $out

mkdir $out/Log && mv Log/*.txt $out/Log/ 2>/dev/null

tar -czvf $out.tar.gz $out

rm -rf $out
find Log/ ! -name '.gitkeep' -type f -exec rm -f {} \;
