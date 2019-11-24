import sbt.Keys.{libraryDependencies, scalaVersion}

name := "scala-example"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.12.10"
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
    "com.typesafe.akka" %% "akka-http-core" % "10.1.10",
    "com.typesafe.akka" %% "akka-http-jackson" % "10.1.10",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10",

    "org.scalactic" %% "scalactic" % "3.0.7",
    "org.scalatest" %% "scalatest" % "3.0.7" % "test"
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
    "io.reactivex" %% "rxscala" % "0.26.5",

    "org.scalactic" %% "scalactic" % "3.0.7",
    "org.scalatest" %% "scalatest" % "3.0.7" % "test"
  )
)

lazy val scalatra_example = (project in file("scalatra-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % "2.6.5",
    "org.scalatra" %% "scalatra-scalate" % "2.6.5",
    "org.eclipse.jetty" % "jetty-server" % "9.4.15.v20190215",
    "org.eclipse.jetty" % "jetty-servlet" % "9.4.15.v20190215",
    "org.eclipse.jetty" % "jetty-webapp" % "9.4.15.v20190215",
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

lazy val akkaVersion = "2.5.26"
lazy val playVersion = "2.7.3"
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
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    )
  )


lazy val slisp = (project in file("slisp")).
  settings(
    commonSettings
    // other settings
  )

lazy val springBootVersion = "2.2.1.RELEASE"
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
      "javax.persistence" % "javax.persistence-api" % "2.2",
      "mysql" % "mysql-connector-java" % "8.0.18",
      "org.apache.tomcat" % "tomcat-jdbc" % "9.0.27",
    )
  )

