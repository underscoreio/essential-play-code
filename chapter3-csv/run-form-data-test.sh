#!/usr/bin/env bash

curl 'http://localhost:9000' \
     --data-binary '@test.formdata' \
     --header 'Content-Type: application/x-www-form-urlencoded' \
     --verbose
