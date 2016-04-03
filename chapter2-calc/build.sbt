lazy val chat = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies in chat += "org.scalatestplus" %% "play" % "1.2.0" % "test"
