package models

import com.rethinkscala.Async._
import com.rethinkscala.ast.Table
import com.rethinkscala.net.Version3

import scala.concurrent.Future

/**
 * Created by anand on 5/10/15.
 */
class EmployeeDAO {

  implicit val asyncConnection = Async(Version3())
  val table: Table[Employee] = r.tableAs[Employee]("employee")
  //val createTable = table.create.run

}
