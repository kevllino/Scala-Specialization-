package observatory

case class Location(lat: Double, lon: Double)

case class Color(red: Int, green: Int, blue: Int)

object Color {

  def interpolateRGB(t0: Double, c0: Int, t1: Double, c1: Int, temperature: Double): Int = {
    (c0 + (temperature - t0) / (t1 - t0) * (c1 - c0)).toInt
  }
}

case class Station(stn: String, wban: String, latitude: Double, longitude: Double)

case class WeatherRecord(stn: String, wban: String, month: Int, day: Int, temperature: Double)
