lazy val chat = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies in chat ++= Seq(
  "org.webjars"       %  "bootstrap" % "3.0.2",
  "org.scalatestplus" %% "play"      % "1.2.0"  % "test"
)
