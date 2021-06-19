#!/bin/bash

rm -f *.csv *.txt *.lp *.graph *.graph.*

find Log/ ! -name '.gitkeep' -type f -exec rm -f {} \;
find Stats/ ! -name '.gitkeep' -type f -exec rm -f {} \;
find latencies/ ! -name '.gitkeep' -type f -exec rm -f {} \;

