import sbt._

object Version {
  val spark        = "1.2.0"
  val sparkstreaming = "1.2.0"
  val hadoop       = "2.4.0"
  val slf4j        = "1.7.6"
  val logback      = "1.1.1"
  val scalaTest    = "2.1.0"
  val mockito      = "1.9.5"
}

object Library {
  val sparkCore      = "org.apache.spark"  %% "spark-core"      % Version.spark
  val sparkStreaming = "org.apache.spark"  %% "spark-streaming"      % Version.spark
  val sparkMqtt      = "org.apache.spark"  %% "spark-streaming-mqtt"      % Version.spark
  val mqttClient     = "org.eclipse.paho"  %  "mqtt-client"     % "0.4.0"
  val hadoopClient   = "org.apache.hadoop" %  "hadoop-client"   % Version.hadoop
  val slf4jApi       = "org.slf4j"         %  "slf4j-api"       % Version.slf4j
  val logbackClassic = "ch.qos.logback"    %  "logback-classic" % Version.logback
  val scalaTest      = "org.scalatest"     %% "scalatest"       % Version.scalaTest
  val mockitoAll     = "org.mockito"       %  "mockito-all"     % Version.mockito
}

object Dependencies {

  import Library._

  val sparkHadoop = Seq(
    sparkCore,
    sparkStreaming,
    sparkMqtt,
    mqttClient,
    hadoopClient,
    logbackClassic % "test",
    scalaTest % "test",
    mockitoAll % "test"
  )
}
