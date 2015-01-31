//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := """spark-sreaming-toy"""

// 2.11 doesn't seem to work
scalaVersion := "2.10.4"

lazy val acquistion = project.in(file("data-acquisition")) //.enablePlugins(PlayScala)

lazy val root = (project in file("."))

libraryDependencies ++= Dependencies.sparkHadoop

libraryDependencies ++= Seq(
  "com.rabbitmq" % "amqp-client" % "3.4.3",
  "com.typesafe.akka" %% "akka-actor" % "2.2.4"
)

releaseSettings

scalariformSettings
