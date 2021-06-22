#!/bin/bash

pidstat -r -u -p $(pgrep -f org.fog.examples.DataPlacement) 1>pidstat_`date +%Y%m%d%H%M%S`.log
