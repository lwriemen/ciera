#!/bin/bash
CLASSPATH=~/.m2/repository/io/ciera/io.ciera.runtime/1.1.1/io.ciera.runtime-1.1.1.jar:~/.m2/repository/io/ciera/MicrowaveOven/1.0.0-SNAPSHOT/MicrowaveOven-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH microwaveoven.MicrowaveOvenApplication