package timeusage

import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

@RunWith(classOf[JUnitRunner])
class TimeUsageSuite extends FunSuite with BeforeAndAfterAll {
  def initializeTimeUsage(): Boolean =
    try {
      val spark: SparkSession =
        SparkSession
          .builder()
          .appName("Time Usage")
          .config("spark.master", "local")
          .getOrCreate()
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  override def afterAll(): Unit = {
    assert(initializeTimeUsage(), " -- did you fill in all the values in TimeUsage (conf, sc)?")
    import TimeUsage._
    spark.stop()
  }

  test("test create - headers and df are not empty") {
    assert(initializeTimeUsage(), " -- did you fill in all the values in TimeUsage (conf, sc)?")
    import TimeUsage._

    val (columns, initDf) = read("/timeusage/atussum.csv")
    assert(!columns.isEmpty)
    assert(initDf.rdd.count() != 0)
  }

}
