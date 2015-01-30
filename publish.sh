#!/usr/bin/env bash

branches=("exercises" "solutions")

for branch in "${branches[@]}"
do
  echo "========== $branch =========="
  git checkout $branch
  git branch -D nohistory
  git checkout --orphan nohistory
  git commit -m "Published $branch"
  git push --force public "nohistory:$branch"
done

git checkout exercises
