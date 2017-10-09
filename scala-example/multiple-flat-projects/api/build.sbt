name := "api"

val scalatraVersion = "2.5.1"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % scalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.6.v20170531",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)
