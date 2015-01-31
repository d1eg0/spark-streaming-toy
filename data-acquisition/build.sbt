name := """data-acquistion"""

version := "1.0-SNAPSHOT"

//lazy val listener = project.in(file("listener"))

//lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.rabbitmq" % "amqp-client" % "3.4.3",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "ch.qos.logback"    %  "logback-classic" % "1.1.1" 
)
