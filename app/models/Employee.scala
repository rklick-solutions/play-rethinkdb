package models

import java.util.UUID

import com.rethinkscala.Document

/**
 * Created by anand on 5/10/15.
 */
case class Employee(id: String, name: String, address: String, designation: String) extends Document

object EmpList {

  val emps: List[Employee] = (1 to 10).map { id =>
    Employee(UUID.randomUUID().toString, s"$id Name", s"$id Address", s"$id Designation")
  }.toList

}
