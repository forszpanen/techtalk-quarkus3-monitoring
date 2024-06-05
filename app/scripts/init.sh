#!/bin/bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.11.0:create \
    -DprojectGroupId=com.capgemini \
    -DprojectArtifactId=quarkus3-monitoring \
    -Dextensions='rest,logging-gelf' \
    -DnoCode