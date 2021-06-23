#!/bin/bash

out=out_`date +%Y%m%d%H%M%S`

mkdir $out

mv *.csv $out 2>/dev/null
mv *.txt $out 2>/dev/null
mv *.lp $out 2>/dev/null
mv *.graph $out 2>/dev/null
mv *.graph.* $out 2>/dev/null
mkdir $out/Log && mv Log/*.txt $out/Log/
mkdir $out/Stats && mv Stats/*_1 $out/Stats/
mkdir $out/latencies && mv latencies/*.txt $out/latencies/

tar -czvf $out.tar.gz $out

#rm -rf $out
#find Log/ ! -name '.gitkeep' -type f -exec rm -f {} \;
#find Stats/ ! -name '.gitkeep' -type f -exec rm -f {} \;
#find latencies/ ! -name '.gitkeep' -type f -exec rm -f {} \;
