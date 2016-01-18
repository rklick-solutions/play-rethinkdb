```
        _                                    _    _      _         _         _  _     
       | |                                  | |  | |    (_)       | |       | || |    
 _ __  | |  __ _  _   _  ______  _ __   ___ | |_ | |__   _  _ __  | | __  __| || |__  
| '_ \ | | / _` || | | ||______|| '__| / _ \| __|| '_ \ | || '_ \ | |/ / / _` || '_ \ 
| |_) || || (_| || |_| |        | |   |  __/| |_ | | | || || | | ||   < | (_| || |_) |
| .__/ |_| \__,_| \__, |        |_|    \___| \__||_| |_||_||_| |_||_|\_\ \__,_||_.__/ 
| |                __/ |                                                              
|_|               |___/                                                               

This activator project describes a classic CRUD application with Play 2.4.x, Scala and RethinkDB
```
-----------------------------------------------------------------------
Building Reactive Play application with RethinkDB

This is a CRUD application, backed by a RethinkDB database. It demonstrates:
- Handling asynchronous results, Handling time-outs
- Achieving, Futures to use more idiomatic error handling.
- Accessing RethinkDB, using Java ReQL command reference.
- Achieving, table pagination and sorting functionality using interactive FooTable plugin.
- Replaced the embedded JS & CSS libraries with [WebJars](http://www.webjars.org/).
- Play and Scala-based template engine implementation
- Integrating with a CSS framework (Twitter Bootstrap). Twitter Bootstrap requires a different form layout to the default one that the Play form helper generates, so this application also provides an example of integrating a custom form input constructor.
- Used Bootswatch with Twitter Bootstrap to improve the look and feel of the application

-----------------------------------------------------------------------
### Dependency
-----------------------------------------------------------------------
The rethinkdb-driver is distributed using Maven Central so it can be easily added as a library dependency in your Play Application's SBT build scripts, as follows:

```
libraryDependencies += "com.rethinkdb" % "rethinkdb-driver" % "2.2-beta-1"
```

-----------------------------------------------------------------------
#### RethinkDb
-----------------------------------------------------------------------
* Installing RethinkDB : [install](http://rethinkdb.com/docs/install/)
* Installing the Java driver : [rethinkdb-driver](http://rethinkdb.com/docs/install-drivers/java/)
* Ten-minute guide with RethinkDB and Java : [RethinkDB Java](http://rethinkdb.com/docs/guide/java/)
* Start RethinkDB using `rethinkdb` command and hit `http://localhost:8080/`
* Create `playing_rethinkdb` database and table `r_employees` with default configuration

##### Usage of RethinkDB Java driver
You can use the drivers from Java like this:

```
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;

public static final RethinkDB r = RethinkDB.r;

Connection conn = r.connection().hostname("localhost").port(28015).connect();

r.db("test").tableCreate("tv_shows").run(conn);
r.table("tv_shows").insert(r.hashMap("name", "Star Trek TNG")).run(conn);
```

Please check [RConnection.scala](app/models/rdb/RConnection.scala) and [RDatabase.scala](app/models/rdb/RDatabase.scala)

-----------------------------------------------------------------------
### Now Play
-----------------------------------------------------------------------
* The Github code for the project is at : [play-rethinkdb](https://github.com/rklick-solutions/play-rethinkdb)
* Clone the project into local system
* To run the Play framework 2.4.x, you need JDK 8 or later
* Install Typesafe Activator if you do not have it already. You can get it from here: http://www.playframework.com/download
* Execute `activator clean compile` to build the product
* Execute `activator run` to execute the product
* play-rethinkdb should now be accessible at localhost:9000

-----------------------------------------------------------------------
### References
-----------------------------------------------------------------------
* [Play 2.4.x](https://playframework.com/documentation/2.4.x/ScalaHome)
* [Java ReQL](http://rethinkdb.com/api/java/)
* [Bootstrap](http://getbootstrap.com/css/)
* [Bootswatch](http://bootswatch.com/)
* [WebJars](http://www.webjars.org/)
