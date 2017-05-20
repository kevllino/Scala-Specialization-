package observatory

import java.time.LocalDate

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import observatory.Extraction._
import Main._

@RunWith(classOf[JUnitRunner])
class ExtractionTest extends FunSuite {
  test("locateTemperatures returns the expected sequence of triplets of (Date, Location, Temperature)") {
    val expectedResults = Iterable(
      (LocalDate.of(2005, 1,11), Location(+59.980,+002.250), convertFahrenheit2Degree(64)),
      (LocalDate.of(2005, 1,13), Location(+80.050,+016.250), convertFahrenheit2Degree(55)),
      (LocalDate.of(2005, 2,2), Location(+64.850,+011.233),convertFahrenheit2Degree(85)),
      (LocalDate.of(2005, 4,16), Location(+61.383,+005.867), convertFahrenheit2Degree(105)),
      (LocalDate.of(2005, 5,15), Location(+59.792,+005.341), convertFahrenheit2Degree(95)),
      (LocalDate.of(2005, 10,11), Location(+59.980,+002.250), convertFahrenheit2Degree(73))
    )

    val actualResults: Iterable[(LocalDate, Location, Double)] = locateTemperatures(2005, "/stations.csv", "/sample.csv")
    assert(expectedResults === actualResults)
  }

  test("locationYearlyAverageRecords returns the expected sequence of triplets of (Date, Location, Temperature)") {
    val input = Iterable(
      (LocalDate.of(2005, 1,11), Location(+59.980,+002.250), convertFahrenheit2Degree(64)),
      (LocalDate.of(2005, 1,13), Location(+80.050,+016.250), convertFahrenheit2Degree(55)),
      (LocalDate.of(2005, 2,2), Location(+64.850,+011.233),convertFahrenheit2Degree(85)),
      (LocalDate.of(2005, 4,16), Location(+61.383,+005.867), convertFahrenheit2Degree(105)),
      (LocalDate.of(2005, 5,15), Location(+59.792,+005.341), convertFahrenheit2Degree(95)),
      (LocalDate.of(2005, 10,11), Location(+59.980,+002.250), convertFahrenheit2Degree(73))
    )

    val expectedAvgTempPerLocation = Iterable(
      (Location(80.05,16.25),12.777777777777779),
        (Location(59.98,2.25),20.27777777777778),
        (Location(64.85,11.233),29.444444444444446),
        (Location(59.792,5.341),35.0),
        (Location(61.383,5.867),40.55555555555556)
    )
    val actualAvgTempPerLocation = locationYearlyAverageRecords(input)
    assert(expectedAvgTempPerLocation === actualAvgTempPerLocation)
  }
  
}