scalaVersion := "2.12.6"

val jodaTime          = "joda-time"               % "joda-time"          % "2.10.1"
val bootstrapJs       = "org.webjars"             % "bootstrap"          % "3.0.2"
val scalatestPlusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1"
val postgresqlJdbc    = "org.postgresql"          % "postgresql"         % "42.2.5"
val playJson          = "com.typesafe.play"      %% "play-json"          % "2.6.13"
val anorm             = "com.typesafe.play"      %% "anorm"              % "2.5.3"

lazy val chapter1Hello =
  project
    .in(file("chapter1Hello"))
    .enablePlugins(PlayScala)

lazy val chapter2Calc =
  project
    .in(file("chapter2Calc"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      jodaTime
    ))

lazy val chapter2Csv =
  project
    .in(file("chapter2Csv"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      jodaTime
    ))

lazy val chapter2Time =
  project
    .in(file("chapter2Time"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      jodaTime
    ))

lazy val chapter2Chat =
  project
    .in(file("chapter2Chat"))
    .enablePlugins(PlayScala, SbtWeb)
    .settings(libraryDependencies ++= Seq(
      guice,
      bootstrapJs,
      scalatestPlusPlay % Test
    ))

lazy val chapter3TodoForm =
  project
    .in(file("chapter3TodoForm"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      "org.webjars" % "bootstrap" % "3.0.0",
      "joda-time"   % "joda-time" % "2.0"
    ))

lazy val chapter3TodoView =
  project
    .in(file("chapter3TodoView"))
    .settings(libraryDependencies ++= Seq(
      guice,
      "org.webjars" % "bootstrap" % "3.0.0",
      "joda-time"   % "joda-time" % "2.0"
    ))

lazy val chapter3Chat =
  project
    .in(file("chapter3Chat"))
    .enablePlugins(PlayScala, SbtWeb)
    .settings(libraryDependencies ++= Seq(
      guice,
      bootstrapJs,
      scalatestPlusPlay % Test
    ))

lazy val chapter4Animals =
  project
    .in(file("chapter4Animals"))
    .settings(libraryDependencies ++= Seq(
      guice,
      playJson,
      scalatestPlusPlay % Test
    ))

lazy val chapter4Color =
  project
    .in(file("chapter4Color"))
    .settings(libraryDependencies ++= Seq(
      guice,
      playJson,
      scalatestPlusPlay % Test
    ))

lazy val chapter4Lights =
  project
    .in(file("chapter4Lights"))
    .settings(libraryDependencies ++= Seq(
      guice,
      playJson,
      scalatestPlusPlay % Test
    ))

lazy val chapter4Macro =
  project
    .in(file("chapter4Macro"))
    .settings(libraryDependencies ++= Seq(
      guice,
      playJson,
      scalatestPlusPlay % Test
    ))

lazy val chapter4Chat =
  project
    .in(file("chapter4Chat"))
    .enablePlugins(PlayScala, SbtWeb)
    .settings(libraryDependencies ++= Seq(
      guice,
      bootstrapJs,
      scalatestPlusPlay % Test
    ))

lazy val chapter5Currency =
  project
    .in(file("chapter5Currency"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice
    ))

lazy val chapter5Weather =
  project
    .in(file("chapter5Weather"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      ws
    ))

lazy val chapter5ChatCommon =
  project
    .in(file("chapter5ChatCommon"))
    .enablePlugins(PlayScala, SbtWeb)
    .settings(libraryDependencies ++= Seq(
      guice,
      ws,
      scalatestPlusPlay % Test
    ))

lazy val chapter5ChatApi =
  project
    .in(file("chapter5ChatApi"))
    .enablePlugins(PlayScala, SbtWeb)
    .dependsOn(chapter5ChatCommon)
    .settings(libraryDependencies ++= Seq(
      guice,
      scalatestPlusPlay % Test
    ))

lazy val chapter5ChatAuthApi =
  project
    .in(file("chapter5ChatAuthApi"))
    .enablePlugins(PlayScala, SbtWeb)
    .dependsOn(chapter5ChatCommon)
    .settings(libraryDependencies ++= Seq(
      guice,
      scalatestPlusPlay % Test
    ))

lazy val chapter5ChatSite =
  project
    .in(file("chapter5ChatSite"))
    .enablePlugins(PlayScala, SbtWeb)
    .dependsOn(chapter5ChatCommon)
    .settings(libraryDependencies ++= Seq(
      guice,
      bootstrapJs,
      ws,
      scalatestPlusPlay % Test
    ))

lazy val extrasAnorm =
  project
    .in(file("extrasAnorm"))
    .enablePlugins(PlayScala)
    .settings(libraryDependencies ++= Seq(
      guice,
      jdbc,
      anorm,
      postgresqlJdbc,
      bootstrapJs,
      jodaTime,
      scalatestPlusPlay % Test
    ))

lazy val root =
  project
    .in(file("."))
    .aggregate(
      chapter1Hello,
      chapter2Calc,
      chapter2Csv,
      chapter2Time,
      chapter2Chat,
      chapter3TodoForm,
      chapter3TodoView,
      chapter3Chat,
      chapter4Animals,
      chapter4Color,
      chapter4Lights,
      chapter4Macro,
      chapter4Chat,
      chapter5Currency,
      chapter5Weather,
      chapter5ChatCommon,
      chapter5ChatApi,
      chapter5ChatAuthApi,
      chapter5ChatSite,
      extrasAnorm,
    )