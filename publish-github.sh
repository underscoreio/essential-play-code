#!/usr/bin/env bash

branches=("exercises" "solutions")

for branch in "${branches[@]}"
do
  echo "========== $branch =========="
  git checkout $branch
  git branch -D nohistory
  git checkout --orphan nohistory
  git commit -m 'Initial commit'
  git push github ":$branch"
  git push --force github "nohistory:$branch"
done

git checkout exercises