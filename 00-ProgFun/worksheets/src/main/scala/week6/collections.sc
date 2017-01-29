val vec =  Vector(1,2,3,-88)
// prepend
59 +: vec
// append
vec :+ 34

val xs = Array(56,2,3,44)
xs map (_ * 2)

val s = "hii Hii"
s filter (_.isUpper)

// represents a sequence of evenly spaced integers
val r: Range = 1 to 5
val d: Range = 1 until 5
1 to 10 by 3

val pairs = List(1,2,3) zip List("a","v","c")
pairs.unzip

s flatMap (c => List('.',c) )

(1 to 9) flatMap (x => (1 to 5) map (y => (x, y)))

def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  (xs zip ys).map{ case (x, y) => x * y }.sum


def isPrime(n: Int): Boolean =
  !((2 until n) exists ( n % _ == 0))

isPrime(11)
