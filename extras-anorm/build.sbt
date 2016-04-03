lazy val chat = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "org.postgresql"    %  "postgresql" % "9.3-1101-jdbc4",
  "org.webjars"       %  "bootstrap"  % "3.0.2",
  "org.scalatestplus" %% "play"       % "1.2.0"           % "test"
)
