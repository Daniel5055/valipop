#!/bin/sh

if [ -n "$1" ];
then
    export MAVEN_OPTS="-Xmx"$1"G"
    echo Setting heap size: $1GB
fi

mvn exec:java -q -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="uk.ac.standrews.cs.digitising_scotland.linkage.tools.GenerateEventRecordsFromDB" -e -Dexec.args="$2"
