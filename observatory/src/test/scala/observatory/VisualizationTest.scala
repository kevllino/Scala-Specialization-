package observatory


import java.time.LocalDate

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import Extraction._
import Visualization._

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers {

  val year = 2005
  val stationsFile = "/stations.csv"
  val temperatureFile = "/sample.csv"
  val someWhereInRussia = Location(50.0, 60.0)

  lazy val locatedTemperatures: Iterable[(LocalDate, Location, Double)] = locateTemperatures(year, stationsFile, temperatureFile)
  lazy val averageTempPerLocation: Iterable[(Location, Double)] = locationYearlyAverageRecords(locatedTemperatures)


  test("Distance between 2 locations"){
    val EARTH_CIRCUM = 2 * math.Pi * 6371.0

    val northPole = Location(90, 0)
    val southPole = Location(-90, 0)

    assert(distance(northPole, southPole) === EARTH_CIRCUM / 2)

  }

  ignore("Predict temperature") {
   val predictedTemp = predictTemperature(averageTempPerLocation, someWhereInRussia)
    println(locatedTemperatures)
    println(averageTempPerLocation)
    println(s"Pred temp is $predictedTemp")

  }

}
