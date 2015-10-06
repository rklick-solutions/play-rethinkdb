package models

import com.rethinkscala.Async._
import com.rethinkscala.ast.Table
import com.rethinkscala.net.Version3
import play.api.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by anand on 5/10/15.
 */
class EmployeeDAO {

  val log = Logger(this.getClass)

  implicit val asyncConnection = Async(Version3())
  val empTable: Table[Employee] = r.db("test").table[Employee]("employee")

  def list(): Future[Employee] = {
    empTable.get("67ffc01d-a193-42fb-97f9-e57235dd6c49").run
  }

  def create(employee: Employee): Future[Int] = {
    log.error(s"##########################Employee = ${employee}")
    empTable.insert(employee).run.map(_.inserted)
  }


}
