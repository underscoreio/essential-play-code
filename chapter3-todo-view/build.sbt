lazy val app = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"org.webjars" % "bootstrap" % "3.0.0",
  "joda-time"   % "joda-time" % "2.0"
)
