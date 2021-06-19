#!/bin/bash

out=out_`date +%Y%m%d%H%M%S`

mkdir $out

mv *.csv $out 2>/dev/null
mv *.txt $out 2>/dev/null
mv *.lp $out 2>/dev/null
mv *.graph $out 2>/dev/null
mv *.graph.* $out 2>/dev/null
mkdir $out/Log && mv Log/* $out/Log/* 2>/dev/null
mkdir $out/Stats && mv Stats/* $out/Stats/*_* 2>/dev/null
mkdir $out/latencies && mv latencies/*.txt $out/latencies/* 2>/dev/null

tar -czvf $out.tar.gz $out

rm -rf $out
find Log/ ! -name '.gitkeep' -type f -exec rm -f {} \;
find Stats/ ! -name '.gitkeep' -type f -exec rm -f {} \;
find latencies/ ! -name '.gitkeep' -type f -exec rm -f {} \;
