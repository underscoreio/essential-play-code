#!/usr/bin/env bash

curl 'http://localhost:9000' --data-binary @'test.tsv' -H 'Content-Type: text/tsv'
