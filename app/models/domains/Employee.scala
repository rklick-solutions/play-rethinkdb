package models.domains

import play.api.libs.json.Json

/**
  * Created by anand on 5/10/15.
  */
case class Employee(id: String, name: String, address: String, designation: String) {

  implicit val empFormat = Json.format[Employee]

  def toJson = Json.toJson(this)

}
