#!/usr/bin/bash

if [ $# -eq 0 ]; then
    ./gradlew run
else
    eval ./gradlew run --args=\"$@\"
fi
