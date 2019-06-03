package ar.edu.unq.pdes.todolist

import ar.edu.unq.pdes.todolist.Requests._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class MarkAsDoneSimulation extends TodosBaseSimulation {

  val scn = scenario("Mark as Done") // A scenario is a chain of requests and pauses
    .exec(session => session.set("ids", Seq[Int]()))
    .repeat(10) {
      exec(createTodo().check(jsonPath("$.id").ofType[Int].saveAs("currentId")))
      .exec(getTodos()).pause(500.milliseconds)
      .exec(markAsDone("currentId"))
      .exec(deleteTodo("currentId"))
    }

  setUp(
    scn.inject(rampUsers(200).during(10.seconds)).protocols(httpProtocol),
  )
}
