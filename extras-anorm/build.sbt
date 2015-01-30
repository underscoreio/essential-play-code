lazy val chat = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "org.postgresql"    %  "postgresql" % "9.3-1101-jdbc4",
  "org.webjars"       %  "bootstrap"  % "3.0.2",
  "joda-time"         %  "joda-time"  % "2.5",
  "org.scalatestplus" %% "play"       % "1.2.0"           % "test"
)
