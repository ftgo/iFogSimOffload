#!/bin/bash

out=out_`date +%Y%m%d%H%M%S`

mkdir $out

mv *.csv $out 2>/dev/null
mv *.txt $out 2>/dev/null
mv *.lp $out 2>/dev/null
mv *.graph $out 2>/dev/null
mv *.graph.* $out 2>/dev/null
mv Log $out 2>/dev/null
mv Stats $out 2>/dev/null
mv latencies $out 2>/dev/null

tar -czvf $out.tar.gz $out

#rm out_*/