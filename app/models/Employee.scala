package models

import java.util.{UUID, Date}

/**
 * Created by anand on 5/10/15.
 */
case class Employee(id: Int, name: String, address: String, dob: Date, joiningDate: Date, designation: String)

object EmpList {

  val emps: List[Employee] = (1 to 10).map { id =>
    Employee(id, s"$id Name", s"$id Address", new Date, new Date, s"$id Designation")
  }.toList

}
