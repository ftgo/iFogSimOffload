#!/bin/bash

pidstat -r -u -p $(pgrep -f org.fog.examples.DataPlacement) 1>Stats/pidstat_`date +%Y%m%d%H%M%S`.log
