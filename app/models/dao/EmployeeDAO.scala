package models.dao

import java.util

import com.rethinkdb.gen.ast.Table
import models.domains.Employee

import scala.async.Async.async
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by anand on 5/10/15.
  */
class EmployeeDAO extends DAO {

  override val table: Table = db.table("r_employees")

  def findAll(): Future[List[Employee]] = async {
    list[util.HashMap[String, String]]() match {
      case Right(employees) => employees map toEmployee
      case Left(err) => error(err); Nil
    }
  }

  def findById(id: String): Future[Option[Employee]] = async {
    filter[util.HashMap[String, String]](db.hashMap("id", id)) match {
      case Right(employees) => employees.headOption.map(toEmployee)
      case Left(err) => error(err); None
    }
  }

  def create(emp: Employee): Future[Option[Employee]] = async {
    insert(db.hashMap("name", emp.name).`with`("address", emp.address).`with`("designation", emp.designation)) match {
      case Right(ids) => ids.headOption.map { id => emp.copy(id = id) }
      case Left(err) => error(err); None
    }
  }

  def update(id: String, emp: Employee): Future[Boolean] = async {
    val mapObj = db.hashMap("name", emp.name).`with`("address", emp.address).`with`("designation", emp.designation)
    updateById(id, mapObj) match {
      case Right(status) => status
      case Left(err) => error(err); false
    }
  }

  private def toEmployee(v: util.HashMap[String, String]): Employee = {
    Employee(v.get("id"), v.get("name"), v.get("address"), v.get("designation"))
  }

}
