package utils

import com.typesafe.config.ConfigFactory

/**
  * Created by anand on 11/15/15.
  */
object AppConfig {

  lazy val typesafeConfig = ConfigFactory.load("application.conf")

  def getString(key: String): String = typesafeConfig.getString(key)

  def getInt(key: String): Int = typesafeConfig.getInt(key)

  /**
    * RethinkDB Configuration
    */
  val RETHINK_DB_HOST = getString("rethinkdb.host")
  val RETHINK_DB_PORT = getInt("rethinkdb.port")
  val RETHINK_DB_NAME = getString("rethinkdb.db")

}
