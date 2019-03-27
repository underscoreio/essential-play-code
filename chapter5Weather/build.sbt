lazy val weather = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies += ws
