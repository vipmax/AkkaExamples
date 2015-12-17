package example1

import example2.actors.Task
import akka.actor.{Actor, ActorSystem, Props}

import scala.util.Random

/**
  * Created by Max on 17.12.2015.
  */

case class Task(info: String)

case class TaskResult(info: String, value: Double)

class TeacherActor extends Actor {
  val task = Task("Count something, I don't know what exactly I want")
  val students = context.actorSelection("/user/Student")

  override def preStart = {
    println(s"Teacher: Hahaha task :) $task")
    students ! task
  }

  override def receive = {
    case taskResult: TaskResult =>
      println("MMM task result :)" + taskResult)

      taskResult.value match {
        case v: Double if v <= 1 && v >= -1 =>
          println("Okey, good :)")
        case _ =>
          println("What??? O_o Go back!")
          sender ! task
      }
  }
}


class StudentActor extends Actor {
  val teacher = context.actorSelection("/user/Teacher*")

  override def receive = {
    case task: Task =>
      println("Student: Uuuhm got task :( " + task)
      val taskResult = TaskResult("Correlation between x and y is", Random.nextDouble() + 0.5)
      println("I'm finished" + taskResult)
      teacher ! taskResult
  }
}


object ExampleRunner {
  def main(args: Array[String]) {
    val actorSystem = ActorSystem()
    actorSystem.actorOf(Props[StudentActor], "Student")
    actorSystem.actorOf(Props[TeacherActor], "Teacher")
  }
}