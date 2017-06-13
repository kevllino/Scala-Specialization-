package observatory

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import Interaction._

import scala.collection.concurrent.TrieMap

@RunWith(classOf[JUnitRunner])
class InteractionTest extends FunSuite with Checkers {

  test("tileLocation should work"){

    val actualLocation = tileLocation(17, 65544, 43582)
    val expectedLocation = Location(51.512161249555156,0.02197265625)
    // 51.512161249555156
    assert(actualLocation === expectedLocation)

  }
  //Tile(65544,43582,17)
  // LatLonPoint(51.51202,0.02435,17)


}
