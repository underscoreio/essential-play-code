# Essential Play Code

This repository contains sample code, exercises, and solutions for Underscore's Essential Play, available as a book or training course from our [web site](http://underscore.io/training/courses/essential-play/).

If you've purchased the book and want to discuss the content or exercises with the authors or other owners, try our chat room on Gitter:

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/underscoreio/essential-play-code?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Using the Source Code

Each sample, exercise, and solution is stored as a standalone Play project stored in a separate branch in this repository. You will need to have installed Git and Java (we recommend Oracle's Java 7 SDK). We have included a copy of SBT and shell scripts to launch it. SBT should install all other required dependencies on demand.

See below for quick getting started instructions. For detailed instructions on using SBT see Chapter 2 of the book.

### Getting Started on Linux or OS X

Complete the following steps outlined in Chapter 2 in the section entitled "Setting up SBT for This Book":

1. Clone this repository to a directory on your hard drive, e.g. `C:\essential-play-code`:

   ~~~
   bash$ git clone https://github.com/underscoreio/essential-play-code.git
   ~~~

2. Change to the relevant directory:

   ~~~
   bash$ cd essential-play-code
   ~~~

3. Run the `sbt.sh` script. You may have to wait while SBT downloads various dependencies:

   ~~~
   bash$ ./sbt.sh
   # Lots of output here...
   # The first run will take a while...

   [app] $
   ~~~

4. Type `run` at the SBT prompt. You may have to wait while SBT downlaods various dependencies:

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

5. Open [http://localhost:9000](http://localhost:9000) in a web browser. SBT will compile the application, which may take a while. After this you should see the message `"Hello World!"` in your browser.

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

### Getting Started on Windows

You will need to have installed Git and Java (we recommend Oracle's Java 7 SDK). Complete the following steps outlined in Chapter 2 in the section entitled "Setting up SBT for This Book":

1. Clone this repository to a directory on your hard drive, e.g. `C:\essential-play-code`:

   ~~~
   C:\> git clone https://github.com/underscoreio/essential-play-code.git C:\essential-play-code
   ~~~

2. Open a command prompt and change to the relevant directory:

   ~~~
   C:\> cd\essential-play-code
   ~~~

3. Run the `sbt.bat` script. You may have to wait while SBT downloads various dependencies:

   ~~~
   C:\essential-play-code> sbt
   # Lots of output here...
   # The first run will take a while...

   [app] $
   ~~~

4. Type `run` at the SBT prompt. You may have to wait while SBT downlaods various dependencies:

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

5. Open [http://localhost:9000](http://localhost:9000) in a web browser. SBT will compile the application, which may take a while. After this you should see the message `"Hello World!"` in your browser.

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

### Switching Between Exercises

Each sample, exercise, and solution is stored as a separate branch. Commit, stash, or discard your changes on the current branch, checkout the desired branch, and start SBT with the `sbt.sh` or `sbt.bat` script as appropriate.
