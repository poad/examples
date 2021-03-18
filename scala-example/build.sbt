import sbt.Keys.{libraryDependencies, scalaVersion}

name := "scala-example"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.13.5"
)

lazy val root = (project in file("."))
  .aggregate(
    akka_http_example,
    curring_example,
    rxscala_example,
    scalatra_example,
    hello,
    functional_training01,
    implicit_example,
    springboot_example,
    play2_mvc_example,
    play2_example,
    slisp
  )


lazy val akka_http_example = (project in file("akka-http-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-core" % "10.2.4",
    "com.typesafe.akka" %% "akka-http-jackson" % "10.2.4",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4",

    "org.scalactic" %% "scalactic" % "3.2.5",
    "org.scalatest" %% "scalatest" % "3.2.5" % "test"
  )
)

lazy val curring_example = (project in file("curring-example")).
settings(
  commonSettings
  // other settings
)

lazy val rxscala_example = (project in file("rxscala-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "io.reactivex" %% "rxscala" % "0.27.0",

    "org.scalactic" %% "scalactic" % "3.2.5",
    "org.scalatest" %% "scalatest" % "3.2.5" % "test"
  )
)

lazy val scalatra_example = (project in file("scalatra-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % "2.7.1",
    "org.scalatra" %% "scalatra-scalate" % "2.7.1",
    "org.eclipse.jetty" % "jetty-server" % "9.4.35.v20201120",
    "org.eclipse.jetty" % "jetty-servlet" % "9.4.35.v20201120",
    "org.eclipse.jetty" % "jetty-webapp" % "9.4.35.v20201120",
  )
)

lazy val hello = (project in file("hello")).
settings(
  commonSettings
  // other settings
)

lazy val functional_training01 = (project in file("functional-training01")).
settings(
  commonSettings
  // other settings
)
lazy val implicit_example = (project in file("implicit-example")).
  settings(
    commonSettings
    // other settings
  )

lazy val akkaVersion = "2.6.13"
lazy val playVersion = "2.8.7"
lazy val play2_mvc_example = (project in file("play2-mvc-example"))
  .enablePlugins(PlayScala)
  .settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play" % playVersion
    )
  )
  .enablePlugins(PlayScala)

lazy val play2_example = (project in file("play2-example"))
  .enablePlugins(PlayScala)
  .settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % playVersion,
      guice,

      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )


lazy val slisp = (project in file("slisp")).
  settings(
    commonSettings
    // other settings
  )

lazy val springBootVersion = "2.4.4"
lazy val springboot_example = (project in file("spring-boot-example")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "org.springframework.boot" % "spring-boot-starter-parent" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("org.springframework.boot", "spring-boot-starter-tomcat"),
      "org.springframework.boot" % "spring-boot-starter-jetty" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-data-jpa" % springBootVersion exclude("com.zaxxer", "HikariCP"),
      "org.springframework.boot" % "spring-boot-starter-jersey" % springBootVersion exclude("org.springframework.boot", "spring-boot-starter-tomcat"),
      "org.springframework.boot" % "spring-boot-starter-actuator" % springBootVersion,
      "jakarta.persistence" % "jakarta.persistence-api" % "3.0.0",
      "mysql" % "mysql-connector-java" % "8.0.23",
      "org.apache.tomcat" % "tomcat-jdbc" % "10.0.4",
    )
  )

