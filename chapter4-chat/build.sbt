lazy val chat4 = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies += Seq(
  "org.webjars"       %  "bootstrap" % "3.0.2",
  "org.scalatestplus" %% "play"      % "1.4.0-M3"  % "test"
)
