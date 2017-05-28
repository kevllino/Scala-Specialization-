package observatory

import scala.math._

case class Location(lat: Double, lon: Double) {
  def latRad = toRadians(lat)
  def lonRad = toRadians(lon)
}

case class Color(red: Int, green: Int, blue: Int)

object Color {

  val scale: Iterable[(Double, Color)] = Iterable(
    (60.0, Color(255,255,255)),
    (32.0, Color(255,0,0)),
    (12.0, Color(255,255,0)),
    (0.0,	Color(0,255,255)),
    (-15.0,	Color(0,0,255)),
    (-27.0,	Color(255,0,255)),
    (-50.0,	Color(33, 0,107)),
    (-60.0,	Color(0,0,0)))

  def interpolateRGB(t0: Double, c0: Int, t1: Double, c1: Int, temperature: Double): Int = {
   round((c0 + (temperature - t0) / (t1 - t0) * (c1 - c0))).toInt
  }
}

case class Station(stn: String, wban: String, latitude: Double, longitude: Double)

case class WeatherRecord(stn: String, wban: String, month: Int, day: Int, temperature: Double)
