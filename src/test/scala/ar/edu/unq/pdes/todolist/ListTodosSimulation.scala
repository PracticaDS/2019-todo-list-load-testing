package ar.edu.unq.pdes.todolist

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import Requests._

class ListTodosSimulation extends TodosBaseSimulation {

  val scn = scenario("Get Todos Load Testing") // A scenario is a chain of requests and pauses
    .exec(session => session.set("ids", Seq[Int]()))
    .repeat(5) {
      exec(createTodo().silent.check(jsonPath("$.id").ofType[Int].saveAs("currentId")))
        .exec(session => {
          session.set("ids", session("ids").as[Seq[Int]] :+ session("currentId").as[Int])
        })
    }
    .repeat(10) {
        exec(getTodos()).pause(500.milliseconds)
    }
//    .exec(session => {
//      Expresiones de este tipo pueden ser útiles para debugear
//      println(session("ids").as[Seq[Int]])
//      session
//    })
    .foreach("${ids}", "id") {
      exec(deleteTodo("id").silent)
    }


  setUp(
    scn.inject(rampUsers(200).during(10.seconds)).protocols(httpProtocol),
  )
}
