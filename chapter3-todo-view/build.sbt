lazy val app = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.0"
)
