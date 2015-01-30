#!/usr/bin/env bash

git checkout master
git branch -D nohistory

for branch in `git branch | cut -c2-`
do
  echo "========== $branch =========="
  git checkout $branch
  git branch -D nohistory
  git checkout --orphan nohistory
  git commit -m 'Initial commit'
  git push github ":$branch"
  git push --force github "nohistory:$branch"
done

git checkout master
git branch -D nohistory
