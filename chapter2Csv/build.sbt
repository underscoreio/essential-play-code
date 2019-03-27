lazy val csv = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies += "joda-time" % "joda-time" % "2.0"
