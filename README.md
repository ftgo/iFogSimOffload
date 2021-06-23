# iFogSimOffload
This is an extension of iFogSim that makes it possible to simulate and evaluate data offloading strategies in context of Fog computing and IoT.


### Install and run
* [Download](http://glaros.dtc.umn.edu/gkhome/metis/metis/download) and install Metis
  * Change `IDXTYPEWIDTH` and `REALTYPEWIDTH` to `64` at `include/metis.h`
  * Run: `make config shared=1 prefix=./precompiled && make && make install`
  * Add to `LD_LIBRARY_PATH` (e.g.: `LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/metis-5.1.0/build/Linux-x86_64/precompiled/lib`)
* Download and install CPLEX
  * [CPLEX Community](https://www.ibm.com/analytics/cplex-optimizer)
    * Add to `LD_LIBRARY_PATH` (e.g.: `LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/ibm/ILOG/CPLEX_Studio_Community201/cplex/bin/x86-64_linux`)
    * Some constants must be exchanged at `DataPlacement` class (marked with `TODO`)
  * [CPLEX Academic](https://community.ibm.com/community/user/datascience/blogs/xavier-nodet1/2020/07/09/cplex-free-for-students?cc=br&mhsrc=ibmsearch_a&mhq=CPLEX) (follow licence instructions)
    * Add to `LD_LIBRARY_PATH` (e.g.: `LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/ibm/ILOG/CPLEX_Studio201/cplex/bin/x86-64_linux`)
* Import CPLEX `/opt/ibm/ILOG/CPLEX_Studio201/cplex/lib/cplex.jar` to local Maven (e.g.: `mvn install:install-file -Dpackaging=jar -DgeneratePom=true -Dfile=cplex.jar -DgroupId=ilog -DartifactId=cplex -Dversion=20.1.0.0`)
* Build: `mvn clean install` (no `jars` folder needed)
* Run: `java -jar -Xmx4096m target/ifogsim-jar-with-dependencies.jar`

### Scripts
* `rm.sh`: removes simulation temp files 
* `bk.sh`: backup all simulation temp files
* `stats.py`: generates simulation summaries and plots
* `pidstat.sh`: analyse CPU and memory usage with `pidstat` (part of `sysstat` tools) - needs simulation running

# iFogSimWithDataPlacement (upstream)

This is an extension of iFogSim that makes it possible to simulate and evaluate data placement strategies in context of Fog computing and IoT. This extension uses two external tools:
1- Cplex to compute the data placement which is formulated as a Generalized Assignement Problem (GAP)
2- Metis to partition a graph which models the simulated infrastructure into k-partitions. This is in order to conceive several data placement sub problems hence reducing the overall problem complexity.

This extension involves several data placement strategies: storageModes = Arrays.asList(CloudStorage,ClosestNode,FogStorage,ZoningStorage,GraphPartitionStorage);

FogStorage and ZoningStorage require CPLEX installation and setups, and GraphPartitionStorage requires Metis installation.

For test, you can set storageModes = Arrays.asList(CloudStorage,ClosestNode,FogStorage,ZoningStorage,GraphPartitionStorage);

The path  of the main class is: src/org/fog/examples/DataPlacement.java

Next, various configurations and setups to reuse this extension, are shown.  
1- Clone this repository in your machine.  
2- Install Cplex: there is a free acadimique version.  
3- Add the Cplex Jar:  
4- Install Metis.  
