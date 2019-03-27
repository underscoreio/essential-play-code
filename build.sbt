scalaVersion := "2.12.6"

lazy val hello = project
  .in(file("hello"))
  .enablePlugins(PlayScala)

