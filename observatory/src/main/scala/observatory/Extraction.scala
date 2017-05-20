package observatory

import java.time.LocalDate

import Main._
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType


/**
  * 1st milestone: data extraction
  */

object Extraction {

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Int, stationsFile: String, temperaturesFile: String) = {

    val spark = getSparkSession("extraction")
    import spark.implicits._
    // extract schemas from case classes
    val stationSchema = ScalaReflection.schemaFor[Station].dataType.asInstanceOf[StructType]
    val weatherRecordSchema = ScalaReflection.schemaFor[WeatherRecord].dataType.asInstanceOf[StructType]

    val stationsDf = spark.read.schema(stationSchema).csv(getFilePathFromResource(stationsFile))
    val temperatureDf = spark.read.schema(weatherRecordSchema).csv(getFilePathFromResource(temperaturesFile))


    val joinedDf =
      stationsDf.as("s")
      .join(temperatureDf.as("t"), $"s.stn" <=> $"t.stn" && $"s.wban" <=> $"t.wban") // && $"s.wban" === $"t.wban" when null?
      .where($"s.latitude".isNotNull && $"s.longitude".isNotNull)
      .select($"month", $"day", $"latitude", $"longitude", $"temperature")
      .sort($"month", $"day")
      // .show()

    joinedDf
      .rdd
      .map(line => (
            LocalDate.of(year, line.getInt(0), line.getInt(1)),
            Location(line.getDouble(2), line.getDouble(3)),
            convertFahrenheit2Degree(line.getDouble(4))
            )
          ).toLocalIterator.toIterable
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Double)]): Iterable[(Location, Double)] = {
    records
      .groupBy(_._2)
      .map(el => (el._1, el._2.map(_._3).sum / el._2.size.toDouble))
      .toList.sortBy(_._2)

  }

}
