package observatory

case class Location(lat: Double, lon: Double)

case class Color(red: Int, green: Int, blue: Int)

case class Station(stn: String, wban: String, latitude: Double, longitude: Double)

case class WeatherRecord(stn: String, wban: String, month: Int, day: Int, temperature: Double)
