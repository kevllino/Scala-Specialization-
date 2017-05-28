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

  ignore("Predicting temperature at a known location") {
    val knownLocation = Location(61.383,005.867)
    val predictedTemp = predictTemperature(averageTempPerLocation, knownLocation)

    assert(predictedTemp === 40.55555555555556)

  }

  ignore("predictTemperature works with 1975"){
    val year = 1975
    val stationsFile = "/stations.csv"
    val temperaturesFile = "/1975.csv"

    val lt = locateTemperatures(year, stationsFile, temperaturesFile)
    assert(lt.size === 2177190)
    val lya = locationYearlyAverageRecords(lt)
    assert(lya.size === 8253)

    val arrayOfLatLon = (for {
      lat <- 90 to -89 by -1
      lon <- -180 to 179
    } yield (lat, lon)).toArray

    val arrayOfTemps = arrayOfLatLon.par.map{case (lat, lon) => predictTemperature(lya, Location(lat, lon))}
    assert(arrayOfTemps.size === arrayOfLatLon.size)
  }


  test("interpolateColor() should work with some records"){
    val cs = Seq(
      (200.0, Color(100, 200, 300)),
      (100.0, Color(100, 100, 100))
    ).toIterable

    assert(Color.interpolateRGB(100.0, 100, 200.0, 200, 150.0) === 150)
    assert(Color.interpolateRGB(100.0, 100, 200.0, 200, 100.0) === 100)

    assert(interpolateColor(cs, 250.0) === Color(100, 200, 300))
    assert(interpolateColor(cs, 50.0) === Color(100, 100, 100))

    assert(interpolateColor(Color.scale, 60.0) === Color(255, 255, 255))
    assert(interpolateColor(Color.scale, 32.0) === Color(255, 0, 0))
    assert(interpolateColor(Color.scale, 12.0) === Color(255, 255, 0))

    assert(interpolateColor(Color.scale, 46.0) === Color(255, 128, 128))
    assert(interpolateColor(Color.scale, 22.0) === Color(255, 128, 0))
  }

}
