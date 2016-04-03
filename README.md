# Essential Play Code

This repository contains exercises and solutions for
[Underscore's Essential Play](http://underscore.io/training/courses/essential-play/)
book and training course.

If you want to discuss the content or exercises with the authors,
join us in our chat room on Gitter:

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/underscoreio/scala?utm_source=essential-play-readme&utm_medium=badge&utm_campaign=essential-play)

## Using the Source Code

This repository contains two branches: one for `exercises` and one for `solutions`.
The directory structure is the same in each branch,
with each exercise stored as a standalone Play project in its own directory.

You will need to have Git and Java and an internet connection to run the exercises.
All other dependendencies are either included with the projects
or downloaded on demand during compilation.

See below for quick getting started instructions.
For more detailed instructions see Chapter 1 of the book.

### Note on Using Scala IDE

Older versions of this repo shipped with the `sbteclipse` plugin
to make it easier to set up Scala IDE.

Current best practices recommend installing `sbteclipse` as a global plugin.
I have removed it deom from this repo because it was causing conflicts
for people who were doing the right thing.

See the `sbteclipse` [documentation](https://github.com/typesafehub/sbteclipse)
for instructions on installing it as a global plugin.

### Getting Started on Linux or OS X

Complete the following steps outlined in Chapter 1 in the section entitled
"Setting up SBT for This Book":

1. Clone this repository to a directory on your hard drive,
   e.g. `C:\essential-play-code`:

   ~~~
   bash$ git clone https://github.com/underscoreio/essential-play-code.git
   ~~~

2. Change to the directory for the "hello world" exercise:

   ~~~
   bash$ cd essential-play-code/chapter1-hello
   ~~~

3. Run the `sbt.sh` script.
   You may have to wait while SBT downloads various dependencies:

   ~~~
   bash$ ./sbt.sh
   # Lots of output here...
   # The first run will take a while...

   [app] $
   ~~~

4. Type `run` at the SBT prompt.
   You may have to wait while SBT downlaods various dependencies:

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

5. Open [http://localhost:9000](http://localhost:9000) in a web browser.
   SBT will compile the application, which may take a while.
   After this you should see the message `"Hello World!"` in your browser.

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

### Getting Started on Windows

You will need to have installed Git and Java (we recommend Oracle's Java 7 SDK).
Complete the following steps outlined in Chapter 1 in the section entitled
"Setting up SBT for This Book":

1. Clone this repository to a directory on your hard drive, e.g. `C:\essential-play-code`:

   ~~~
   C:\> git clone https://github.com/underscoreio/essential-play-code.git ↩
                  C:\essential-play-code
   ~~~

2. Change to the directory for the "hello world" exercise:

   ~~~
   C:\> cd\essential-play-code\chapter1-hello
   ~~~

3. Run the `sbt.bat` script.
   You may have to wait while SBT downloads various dependencies:

   ~~~
   C:\essential-play-code> sbt
   # Lots of output here...
   # The first run will take a while...

   [app] $
   ~~~

4. Type `run` at the SBT prompt.
   You may have to wait while SBT downlaods various dependencies:

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~

5. Open [http://localhost:9000](http://localhost:9000) in a web browser.
   SBT will compile the application, which may take a while.
   After this you should see the message `"Hello World!"` in your browser.

   ~~~
   [app] $ run
   # Lots of output here...
   # The first run will take a while...

   --- (Running the application from SBT, auto-reloading is enabled) ---

   [info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

   (Server started, use Ctrl+D to stop and go back to the console...)
   ~~~
