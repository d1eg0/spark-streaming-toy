import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import org.eclipse.paho.client.mqttv3.{ MqttClient, MqttClientPersistence, MqttException, MqttMessage, MqttTopic }
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.mqtt._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "README.md"
    val conf = new SparkConf().setAppName("listener").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val ampqurl = "tcp://127.0.0.1:1883" //"tcp://127.0.0.1:1883"
    val lines = MQTTUtils.createStream(ssc, ampqurl, "messages", StorageLevel.MEMORY_ONLY_SER_2)
    //val sc = new SparkContext("local", "Simple App", "~/oss/spark",
    // List("target/scala-2.10/spark-activator_2.10-1.0.jar"))
    //val logData = sc.textFile(logFile, 2).cache()
    //val numAs = logData.filter(_.contains("a")).count()
    //val numBs = logData.filter(_.contains("b")).count()
    //println(s"Lines with a: $numAs, Lines with b: $numBs")
    val words = lines.flatMap(x => x.toString.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
