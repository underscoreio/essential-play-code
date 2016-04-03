lazy val common = project.in(file("./common")).
  enablePlugins(PlayScala)

scalaVersion in common := "2.11.8"

libraryDependencies in common += ws



lazy val authApi = project.in(file("./authApi")).
  dependsOn(common).
  enablePlugins(PlayScala)

scalaVersion in authApi := "2.11.8"



lazy val chatApi = project.in(file("./chatApi")).
  dependsOn(common).
  enablePlugins(PlayScala)

scalaVersion in chatApi := "2.11.8"



lazy val site = project.in(file("./site")).
  dependsOn(common).
  enablePlugins(PlayScala)

scalaVersion in site := "2.11.8"

libraryDependencies in site += "org.webjars" % "bootstrap" % "3.0.2"



lazy val root = project.in(file(".")).aggregate(
  authApi,
  chatApi,
  site,
  common
)
