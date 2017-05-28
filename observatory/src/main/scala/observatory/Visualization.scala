package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import math.{acos, sin, cos, abs, pow}
import Color._
import scala.math._
/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  val earthRadius = 6371.0
  def distance(loc1: Location, loc2: Location): Double = {
    val greatCircleDistance = acos(sin(loc1.latRad) * sin(loc2.latRad) + cos(loc1.latRad) * cos(loc2.latRad) * cos(abs(loc1.lonRad - loc2.lonRad)))
    greatCircleDistance * earthRadius
  }

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location): Double = {

    val N = temperatures.size

    def weight(loc1: Location, loc2: Location, p: Int = 2): Double = {
      if (loc1 == loc2) 0.0
      else {
        val d = distance(loc1, loc2)
        if (d <= 1) 0.0
        else 1 / pow(d, p)
      }
    }

    val locationWeightsAndTemperatures = temperatures.map{ case (loc, temp) => (weight(loc, location), temp) }
    val knownTemperature = locationWeightsAndTemperatures.find( _._1 == 0)

    val predictedTemperature = if (knownTemperature.isDefined) knownTemperature.get._2
    else {
      val numerator = locationWeightsAndTemperatures.foldLeft(0.0)((acc, currElem) => acc + currElem._1 * currElem._2)
      val denominator = locationWeightsAndTemperatures.foldLeft(0.0)( (acc, currElem) => acc + currElem._1)
      numerator / denominator
    }

    if (predictedTemperature.isNaN) 0.0 else predictedTemperature

  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {


    val sortedPoints = points.toSeq.sortBy(_._1)
    val knownPoint = sortedPoints.find(_._1 == value)

    if (knownPoint.isDefined) knownPoint.get._2
    else if (value > sortedPoints.last._1) sortedPoints.last._2
    else if (value < sortedPoints.head._1) sortedPoints.head._2
    else {
      val beforeAndAfterPoints = sortedPoints.sliding(2).find(p => p.head._1 <= value && value <= p.last._1)
      val (t0, c0) = beforeAndAfterPoints.get.head
      val (t1, c1) = beforeAndAfterPoints.get.last

      Color(
        interpolateRGB(t0, c0.red, t1, c1.red, value),
        interpolateRGB(t0, c0.green, t1, c1.green, value),
        interpolateRGB(t0, c0.blue, t1, c1.blue, value)
      )
    }
  }
//
//  /**
//    * @param temperatures Known temperatures
//    * @param colors Color scale
//    * @return A 360×180 image where each pixel shows the predicted temperature at its location
//    */
//  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)], alpha: Int): Image = {
//     // latitude = x and longitude = y
//    // x: -180 0 179 and y: 90 0 -89
//
//    val topLeft = Location(90, -180)
//    val lowRIght = Location(-89, 179)
//
//    val height = 180
//    val width = 360
//
//    val points: Iterable[Location] = for {
//      lat  <- topLeft.lat to 179
//      lon <-  topLeft.lon to(-89, -1)
//    } yield Location(lat, lon)
//
//    val  pixels: Array[Pixel] = points.map( loc => {
//      val temp = predictTemperature(temperatures, loc)
//      val color = interpolateColor(colors, temp)
//      Pixel(color.red, color.green, color.blue, alpha)
//    }
//    ).toArray
//
//    Image(width, height, pixels)
//
//  }

}

