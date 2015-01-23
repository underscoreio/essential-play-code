#!/usr/bin/env bash

curl 'http://localhost:9000' --data-binary @'test.formdata' -H 'Content-Type: application/x-www-form-urlencoded'