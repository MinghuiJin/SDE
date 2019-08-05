#!/usr/bin/env bash

# In bash comments start with a # symbol, except for the line above which
# you are writing for your operating system. This line tells your Operating
# System which program to use to interpret this file. In this case we're
# telling your operating system to use the bash program to intepret this
# file.

# It is important that this file have the executable permission set in order
# for the operating system facility we employ above to work. Otherwise we will
# need to invoke this file as: `bash bash/runme.sh` rather than simply 
# `./bash/runme.sh`

# This is a canonical way to find bash that will work on most modern unix
# machines including Mac OSX. We're using the `env` program in `/usr/bin`
# to find the version of bash appearing first on the user's $PATH.
# This is the same interpreter the user would get when typing `bash` at
# the command line, and is the "right" choice in most cases.

# You can achieve greater control over which bash you use (in the case of
# multiple bash interpreters) by specifying a path directly like:
# #!/bin/bash OR #!/usr/bin/bash

# We're going to set some options to bash now to control execution of future
# commands. Thisconvention is borrowed from
# http://redsymbol.net/articles/unofficial-bash-strict-mode/
# Together these commands ease debugging and lead to more readable and safer
# bash scripts.

# This stops the script if any command errors
set -o errexit

# This errors any command that uses an unset variable
set -o nounset

# This errors any pipeline command on any of the constituent commands' errors
# rather than only examining the final command in a pipeline to determine the
# error status of the whole thing.
set -o pipefail

# This line is commented out because its optional. If you uncomment it, bash
# will print every command before it executes it. This logging facility can
# be very useful for debugging.
# set -o xtrace

# To work with files we're going to Only store and work with absolute paths.
# This is so our commands do not depend on the current working directory
# and can be run from any directory equally well and unambigously.

# In order to do that we need to first figure out what directory the
# root of the repository is at, so we can use that as a starting point
# to refer to other files. We're going to do this without making assumptions
# about the behavior of whoever called this script. That includes avoiding
# this scripts command line arguments and present working directory.
# We are going to assume this script sits one directory below
# the root of the repository, and that the directory this script sits in
# can be traversed.

declare -r BASE_DIR="$(cd "$(dirname "$BASH_SOURCE")/.." && pwd)"
echo "Repository Root: $BASE_DIR"

# There's a lot going on in this line.
# declare -r is used to declare a read-only variable. That means we can't
# change $BASE_DIR after this line.

# We're starting with $BASH_SOURCE which is a relative path to this file.
# we get the directory part of $BASH_SOURCE and add `/..` so that the `cd`
# command will move us up one level from this file. Once we're there we use
# `pwd` to give us the absolute path at that location.
# The $() syntax runs the enclosed commands inside a subshell so that our
# current context (and current working directory) is unaffected by this.

# Now we use mv and $BASE_DIR to do the actual (trivial) work of this script
# Create the generated dir if it does not exist
mkdir -p "$BASE_DIR/generated"

# Copy all files from data to generated
cp $BASE_DIR/data/* $BASE_DIR/generated/

