# iFogSimWithDataPlacement

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


# This fork
* Download and install CPLEX: https://www.ibm.com/analytics/cplex-optimizer
* Add CPLEX to `LD_LIBRARY_PATH` environment variable or execute with `-Djava.library.path`
    * e.g.: `-Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio_Community201/cplex/bin/x86-64_linux`  
* Better build: `mvn clean install` (no `jars` needed)
    * But first, execute (replacing `<VERSION>`, e.g.: 20.1.0.0) `mvn install:install-file -Dpackaging=jar -DgeneratePom=true -Dfile=cplex.jar -DgroupId=ilog -DartifactId=cplex -Dversion=<VERSION>`

