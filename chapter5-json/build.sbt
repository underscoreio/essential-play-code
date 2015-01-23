// The demo application is called `app`, is situated in
// the current directory, and uses the Play plugin:
lazy val app = project.in(file("."))

// Using Scala 2.10 because the ScalaTest bindings aren't build for 2.11 yet:
scalaVersion in app := "2.10.4"

// Play bindings for ScalaTest, used in the unit tests:
libraryDependencies in app += "org.scalatestplus" %% "play" % "1.3.0" % "test"

// The Play web services client, provided by the Play plugin.
// This is used to connect to localhost in the unit tests:
libraryDependencies in app += ws
