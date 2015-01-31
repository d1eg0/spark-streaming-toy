package controllers

//import play.api._
//import play.api.mvc._
//import akka.Akka //play.libs.Akka
//import play.api.libs.concurrent.Execution.Implicits._
import akka.actor._
import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import akka.actor.ActorContext
import akka.actor.ActorSystem
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import com.typesafe.config.ConfigFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object Config {
  val RABBITMQ_HOST = ConfigFactory.load().getString("rabbitmq.host");
  val RABBITMQ_QUEUE = ConfigFactory.load().getString("rabbitmq.queue");
  val RABBITMQ_EXCHANGEE = ConfigFactory.load().getString("rabbitmq.exchange");
}

object RabbitMQConnection {

  private val connection: Connection = null;

  /**
   *    * Return a connection if one doesn't exist. Else create
   *       * a new one
   *          */
  def getConnection(): Connection = {
    connection match {
      case null => {
        val factory = new ConnectionFactory();
        factory.setHost(Config.RABBITMQ_HOST);
        factory.newConnection();
      }
      case _ => connection
    }
  }
}

object Sender {
  val system = akka.actor.ActorSystem("system")
  import system.dispatcher
  // create the connection
  val connection = RabbitMQConnection.getConnection();
  def startSending = {
    // create the channel we use to send
    val sendingChannel = connection.createChannel();
    // make sure the queue exists we want to send to
    sendingChannel.queueDeclare(Config.RABBITMQ_QUEUE, false, false, false, null);
    // sendingChannel.exchangeDeclare("amq.topic",
    //                           "topic",
    //                           false)
    sendingChannel.queueBind(Config.RABBITMQ_QUEUE,"amq.topic","messages",null)
    system.scheduler.schedule(2 seconds , 1 seconds,
      system.actorOf(Props(
        new SendingActor(channel = sendingChannel, queue = Config.RABBITMQ_QUEUE)
      )
    ),
  "[SENDER] MSG to Queue");
  }
}

class SendingActor(channel: Channel, queue: String) extends Actor {
  val logger = LoggerFactory.getLogger("data-acquistion")
  def receive = {
    case some: String => {
      val msg = (some + " : " + System.currentTimeMillis());
      channel.basicPublish("amq.topic", "messages", null, msg.getBytes());
      logger.info(msg);
    }
    case _ => {}
  }
}

object SimpleApp {
  def main(args: Array[String]) {
    println("hola")
    Sender.startSending
    println("adios")
  }
}

