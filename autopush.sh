#!/bin/bash

git add .
echo "Enter your comment message: "
read message
git commit -m "$message"
git push
