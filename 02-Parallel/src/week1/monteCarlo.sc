import scala.util.Random
import common.parallel

def carlo(iter: Int): Int = {
  val randX = new Random()
  val randY = new Random()
  var hits = 0
  for (i <- 0 until iter){
    val x = randX.nextDouble()
    val y = randY.nextDouble()
    if(x * x + y * y < 1) hits = hits + 1
  }
  hits
}
def monteCarloPiSeq(iter: Int): Double =
  4.0 * carlo(iter) / iter

// monteCarloPiSeq(100000000)

def monteCarloPar(iter: Int): Double = {
  val ((pi1, pi2), (pi3, pi4)) = parallel(
    parallel(carlo(iter / 4), carlo(iter / 4)),
    parallel(carlo(iter / 4), carlo(iter / 4))
  )
  4 * (pi1 + pi2 + pi3 + pi4) / iter
}

// monteCarloPar(100000000)
