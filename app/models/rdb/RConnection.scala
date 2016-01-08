package models.rdb

import utils.AppConfig._

/**
  * Created by anand on 11/15/15.
  */
object RConnection {

  def apply(): RDatabase = {
    new RDatabase(RETHINK_DB_HOST, RETHINK_DB_PORT, RETHINK_DB_NAME)
  }

}
