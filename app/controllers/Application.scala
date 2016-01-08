package controllers

import java.util.UUID
import javax.inject.Inject

import models.dao.EmployeeDAO
import models.domains.Employee
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{ignored, mapping, nonEmptyText}
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import views.html

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Future, TimeoutException}

class Application @Inject()(empDAO: EmployeeDAO, val messagesApi: MessagesApi) extends Controller {

  implicit val timeout = 10.seconds

  /**
    * Describe the employee form (used in both edit and create screens).
    */
  val employeeForm = Form(
    mapping(
      "id" -> ignored(UUID.randomUUID().toString),
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "designation" -> nonEmptyText)(Employee.apply)(Employee.unapply))

  /**
    * Handle default path requests, redirect to employee list
    */
  def index = Action {
    Home
  }

  /**
    * This result directly redirect to the application home.
    */
  val Home = Redirect(routes.Application.list())

  /**
    * Display the list of employees.
    */
  def list() = Action.async { implicit request =>
    empDAO.findAll().map({ employees =>
      implicit val msg = messagesApi.preferred(request)
      Ok(html.list(employees))
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
  def edit(id: String) = Action.async { request =>
    val result = for {
      emp <- empDAO.findById(id)
    } yield {
      emp.map { emp =>
        implicit val msg = messagesApi.preferred(request)
        Ok(html.editForm(id, employeeForm.fill(emp)))
      }.getOrElse(Home.flashing("success" -> s"Employee not found"))
    }

    result.map(res => res).recover {
      case t: TimeoutException =>
        Logger.error("Problem found in employee edit process")
        InternalServerError(t.getMessage)
    }
  }

  /**
    * Handle the 'edit form' submission
    *
    * @param id Id of the employee to edit
    */
  def update(id: String) = Action.async { implicit request =>
    employeeForm.bindFromRequest.fold(
      { formWithErrors =>
        implicit val msg = messagesApi.preferred(request)
        Future.successful(BadRequest(html.editForm(id, formWithErrors)))
      },
      employee => {
        val futureUpdateEmp = empDAO.update(id, employee)
        futureUpdateEmp.map { result =>
          Home.flashing("success" -> s"Employee ${employee.name} has been updated")
        }.recover {
          case t: TimeoutException =>
            Logger.error("Problem found in employee update process")
            InternalServerError(t.getMessage)
        }
      })
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
    employeeForm.bindFromRequest.fold(
      { formWithErrors =>
        implicit val msg = messagesApi.preferred(request)
        Future.successful(BadRequest(html.createForm(formWithErrors)))
      },
      employee => {
        val futureUpdateEmp = empDAO.create(employee)
        futureUpdateEmp.map { result =>
          Home.flashing("success" -> s"Employee ${employee.name} has been created")
        }.recover {
          case t: TimeoutException =>
            Logger.error("Problem found in employee update process")
            InternalServerError(t.getMessage)
        }
      })
  }

  /**
    * Handle employee deletion.
    */
  def delete(id: String) = Action.async {
    val futureResult = Future.successful(empDAO.deleteById(id))
    futureResult.map(i => Home.flashing("success" -> "Employee has been deleted")).recover {
      case t: TimeoutException =>
        Logger.error("Problem deleting employee")
        InternalServerError(t.getMessage)
    }
  }

}
