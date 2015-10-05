package controllers

import java.util.UUID
import javax.inject.Inject

import models.{EmployeeDAO, Employee, Page}
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{date, ignored, mapping, nonEmptyText}
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import views.html

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Future, TimeoutException}

class Application@Inject() (empDAO: EmployeeDAO, val messagesApi: MessagesApi) extends Controller {

  implicit val timeout = 10.seconds

  /**
   * Describe the employee form (used in both edit and create screens).
   */
  val employeeForm = Form(
    mapping(
      "id" -> ignored(0),
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "dob" -> date("yyyy-MM-dd"),
      "joiningDate" -> date("yyyy-MM-dd"),
      "designation" -> nonEmptyText)(Employee.apply)(Employee.unapply))

  /**
   * Handle default path requests, redirect to employee list
   */
  def index = Action { Home }

  /**
   * This result directly redirect to the application home.
   */
  val Home = Redirect(routes.Application.list())

  /**
   * Display the paginated list of employees.
   *
   * @param page Current page number (starts from 0)
   * @param orderBy Column to be sorted
   * @param filter Filter applied on employee names
   */
  def list(page: Int, orderBy: Int, filter: String) = Action.async { implicit request =>
    val futurePage = Future[List[Employee]](Nil)/*if (filter.length > 0) {
      collection.find(Json.obj("name" -> filter)).cursor[Employee]().collect[List]()
    } else collection.genericQueryBuilder.cursor[Employee]().collect[List]()*/

    futurePage.map({ employees =>
      implicit val msg = messagesApi.preferred(request)

      Ok(html.list(Page(Nil, 0, 10, 20), orderBy, filter))
    }).recover {
      case t: TimeoutException =>
        Logger.error("Problem found in employee list process")
        InternalServerError(t.getMessage)
    }
  }

  /**
   * Display the 'edit form' of a existing Employee.
   *
   * @param id Id of the employee to edit
   */
  def edit(id: Int) = Action.async { request =>
    /*val futureEmp = collection.find(Json.obj("_id" -> Json.obj("$oid" -> id))).cursor[Employee]().collect[List]()
    futureEmp.map { emps: List[Employee] =>
      implicit val msg = messagesApi.preferred(request)

      Ok(html.editForm(id, employeeForm.fill(emps.head)))
    }.recover {
      case t: TimeoutException =>
        Logger.error("Problem found in employee edit process")
        InternalServerError(t.getMessage)
    }*/
    Future(Home.flashing("success" -> s"Employee has been created"))
  }

  /**
   * Handle the 'edit form' submission
   *
   * @param id Id of the employee to edit
   */
  def update(id: Int) = Action.async { implicit request =>
    /*employeeForm.bindFromRequest.fold(
    { formWithErrors =>
      implicit val msg = messagesApi.preferred(request)
      Future.successful(BadRequest(html.editForm(id, formWithErrors)))
    },
    employee => {
      val futureUpdateEmp = collection.update(Json.obj("_id" -> Json.obj("$oid" -> id)), employee.copy(_id = BSONObjectID(id)))
      futureUpdateEmp.map { result =>
        Home.flashing("success" -> s"Employee ${employee.name} has been updated")
      }.recover {
        case t: TimeoutException =>
          Logger.error("Problem found in employee update process")
          InternalServerError(t.getMessage)
      }
    })*/
    Future(Home.flashing("success" -> s"Employee has been created"))
  }

  /**
   * Display the 'new employee form'.
   */
  def create = Action { request =>
    implicit val msg = messagesApi.preferred(request)
    Ok(html.createForm(employeeForm))
  }

  /**
   * Handle the 'new employee form' submission.
   */
  def save = Action.async { implicit request =>
    /*employeeForm.bindFromRequest.fold(
    { formWithErrors =>
      implicit val msg = messagesApi.preferred(request)
      Future.successful(BadRequest(html.createForm(formWithErrors)))
    },
    employee => {
      val futureUpdateEmp = collection.insert(employee.copy(_id = BSONObjectID.generate))
      futureUpdateEmp.map { result =>
        Home.flashing("success" -> s"Employee ${employee.name} has been created")
      }.recover {
        case t: TimeoutException =>
          Logger.error("Problem found in employee update process")
          InternalServerError(t.getMessage)
      }
    })*/
    Future(Home.flashing("success" -> s"Employee has been created"))
  }

  /**
   * Handle employee deletion.
   */
  def delete(id: Int) = Action.async {
    /*val futureInt = collection.remove(Json.obj("_id" -> Json.obj("$oid" -> id)), firstMatchOnly = true)
    futureInt.map(i => Home.flashing("success" -> "Employee has been deleted")).recover {
      case t: TimeoutException =>
        Logger.error("Problem deleting employee")
        InternalServerError(t.getMessage)
    }*/
    Future(Home.flashing("success" -> s"Employee has been created"))
  }

}
