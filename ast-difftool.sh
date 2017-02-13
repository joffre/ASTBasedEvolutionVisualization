#!/bin/bash

if [ ${2: -5} == ".java" ] || [ ${3: -5} == ".java" ]
then
    set +e
    java -cp gumtree-spoon-ast-diff.jar fr.inria.sacha.spoon.diffSpoon.DiffSpoonImpl $1 $2 $3

fi
