#!/bin/bash

out=out_`date +%Y%m%d%H%M%S`

mkdir $out

cp -rf *.txt $out 2>/dev/null
cp -rf *.lp $out 2>/dev/null
cp -rf *.graph $out 2>/dev/null
cp -rf *.graph.* $out 2>/dev/null
cp -rf Log $out 2>/dev/null
cp -rf Stats $out 2>/dev/null
cp -rf offload $out 2>/dev/null
cp -rf latencies $out 2>/dev/null

rm -rf *.txt *.lp *.graph *.graph.* Log/* Stats/* offload/* latencies/*