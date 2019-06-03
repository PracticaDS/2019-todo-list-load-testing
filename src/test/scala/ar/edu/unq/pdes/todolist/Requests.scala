package ar.edu.unq.pdes.todolist

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Requests {
  def createTodo(name: String = "a Todo") = {
    http("Create Todo")
      .post("/api/todos")
      .header("Content-Type", "application/json")
      .body(StringBody(s"""{"description": "${name}"}"""))
      .check(status.is(200))
  }

  def getTodos() = {
    http("Get Todos")
      .get("/api/todos")
      .check(status.is(200))
  }

  def deleteTodo(sessionAttributeName: String) = {
    http("Delete Todo")
      .delete(s"/api/todos/$${${sessionAttributeName}}")
      .check(status.is(204))
  }

  def markAsDone(sessionAttributeName: String) = {
    http("Mark as Done")
      .put(s"/api/todos/$${${sessionAttributeName}}")
      .header("Content-Type", "application/json")
      .body(StringBody(s"""{"description": "aTodo", "done": true}"""))
      .check(status.is(200))
  }
}
