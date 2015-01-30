#!/usr/bin/env bash

curl 'http://localhost:9000' \
     --data-binary '@test.tsv' \
     --header 'Content-Type: text/plain' \
     --verbose
