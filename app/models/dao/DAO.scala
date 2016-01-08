package models.dao

import java.util

import com.rethinkdb.gen.ast.Table
import com.rethinkdb.model.MapObject
import com.rethinkdb.net.Cursor
import models.domains.Employee
import models.rdb.RConnection
import utils.AppLogger

import scala.collection.JavaConversions._
import scala.util.control.NonFatal

/**
  * Created by anand on 11/18/15.
  */
trait DAO extends AppLogger {

  val GEN_KEY = "generated_keys"
  val DELETED = "deleted"
  val REPLACED = "replaced"
  val COUNT = 0

  private val rDB = RConnection()

  lazy val db = rDB.r
  lazy val conn = rDB.conn

  val table: Table

  /**
    * Generic function to fetch all data from RethinkDB table
    *
    * @tparam T The Class
    * @return
    */
  def list[T](): Either[String, List[T]] = tryCatchEither {
    table.run[Cursor[T]](conn).toList.toList
  }

  /**
    * Generic function to fetch data from RethinkDB table by criteria
    *
    * @param criteria
    * @tparam T
    * @return
    */
  def filter[T](criteria: MapObject): Either[String, List[T]] = tryCatchEither {
    table.filter(criteria).run[Cursor[T]](conn).toList.toList
  }

  /**
    * Generic insert function to store data in RethinkDB
    *
    * @param obj The Map[String, Object]
    * @return
    */
  def insert(obj: MapObject): Either[String, List[String]] = tryCatchEither {
    table.insert(obj).run[util.HashMap[String, Object]](conn).get(GEN_KEY).asInstanceOf[util.ArrayList[String]].toList
  }

  /**
    * Generic update function to update data in RethinkDB by ID
    *
    * @param obj The Map[String, Object]
    * @return
    */
  def updateById(id: String, obj: MapObject): Either[String, Boolean] = tryCatchEither {
    table.get(id).update(obj).run[util.HashMap[String, Object]](conn).get(REPLACED).asInstanceOf[Long] > COUNT
  }

  /**
    * Generic update function to update data in RethinkDB by criteria
    *
    * @param criteria The Map[String, Object]
    * @param obj The Map[String, Object]
    * @return
    */
  def updateByCriteria(criteria: MapObject, obj: MapObject): Either[String, Boolean] = tryCatchEither {
    table.filter(criteria).update(obj).run[util.HashMap[String, Object]](conn).get(REPLACED).asInstanceOf[Long] > COUNT
  }

  /**
    * Generic delete function to delete data from RethinkDB by ID
    *
    * @param id The String
    * @return
    */
  def deleteById(id: String): Either[String, Boolean] = tryCatchEither {
    table.get(id).delete().run[util.HashMap[String, Object]](conn).get(DELETED).asInstanceOf[Long] > COUNT
  }

  /**
    * Generic delete function to delete data from RethinkDB by criteria
    *
    * @param criteria The Map[String, Object]
    * @return
    */
  def deleteByCriteria(criteria: MapObject): Either[String, Boolean] = tryCatchEither {
    table.filter(criteria).delete().run[util.HashMap[String, Object]](conn).get(DELETED).asInstanceOf[Long] > COUNT
  }

  /**
    * This method handles the try catch block.
    *
    * @param body
    * @tparam T
    * @return
    */
  private def tryCatchEither[T](body: => T): Either[String, T] = {
    try {
      Right(body)
    } catch {
      case NonFatal(t: Throwable) => error(t.getMessage, t); Left(t.getMessage)
    }
  }

}
