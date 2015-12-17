package example2.actors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by Max on 24.10.2015.
  */

case class WorkerUp()
case class Task(taskInfo: String)


class WorkerActor(masterIp: String) extends Actor with ActorLogging {
  val master = context.actorSelection(s"akka.tcp://cluster@$masterIp:2551/user/master")

  override def preStart() = {
    0 to 4 foreach { e => Thread.sleep(1000); println(5 - e) }
    println(s"Sending task request")

    master ! WorkerUp()
  }

  override def receive() = {
    case taskInfo: Task =>
      println(s"Received task")
      println(s"Starting task $taskInfo")
      println(s"Ending task $taskInfo")
  }
}

class MasterActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case WorkerUp() =>
      println(s"$sender Wants task")
      sender ! Task("Some task")
  }
}
