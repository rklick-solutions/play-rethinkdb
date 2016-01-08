package models.rdb

import com.rethinkdb.RethinkDB

/**
  * Created by anand on 11/18/15.
  */
class RDatabase(host: String, port: Int, db: String) {

  val r = RethinkDB.r
  val conn = r.connection().db(db).hostname(host).port(port).connect()

}
