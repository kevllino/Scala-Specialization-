package observatory

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object Main extends App {
  case class Station(
                      stn: String,
                      wban: String,
                      latitude: Double,
                      longitude: Double
                    )

  case class WeatherRecord(
                            stn: String,
                            wban: String,
                            month: Int,
                            day: Int,
                            temperature: Double
                          )

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  def getSparkContext(appName: String): SparkContext = {

    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster("local[*]")

    val sc = SparkContext.getOrCreate(conf)
    sc
  }

  def getSparkSession(appName: String): SparkSession = {
    val sparkSession =
      SparkSession
        .builder()
        .appName(appName)
        .master("local[*]")
        .getOrCreate()
    sparkSession
  }

  def convertFahrenheit2Degree(temperature: Double): Double = {
    (temperature - 32) * (5 / 9.toDouble)
  }

  // resource must have a leading '/'
  // allows to specify the absolute path of a resource
  def getFilePathFromResource(resource: String): String = {
    getClass.getResource(resource).getFile
  }


}
