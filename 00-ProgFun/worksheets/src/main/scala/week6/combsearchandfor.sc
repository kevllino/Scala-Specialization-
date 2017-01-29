def isPrime(n: Int): Boolean =
  !((2 until n) exists ( n % _ == 0))

val xss = (1 until 7) flatMap ( i => (1 until i)
  map (j => (i, j)))

xss filter (pair => isPrime(pair._1 + pair._2))

case class Person(name: String, age: Int)

for {
  i <- 1 until 7 //generators
  j <- 1 until i
  if (isPrime(i+j)) //filters
}
  yield (i, j)


def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
  (for((x, y) <- xs zip ys) yield (x * y)).sum

// sets are unordered, fundamental op = contains
// N - Queens
def queens(n: Int): Set[List[Int]] = {
  def placeQueens(k: Int): Set[List[Int]] =
    if (k == 0) Set(List())
    else
      for {
        queens <- placeQueens(k - 1)
        col <- 0 until n
        if isSafe(col, queens)
      }
      yield col :: queens

  placeQueens(n)

}

def isSafe(col: Int, queens: List[Int]): Boolean = {
  val row = queens.length
  val queensWithRow = (row - 1 to 0 by -1 ) zip queens
  queensWithRow forall {
    case (r, c) => col != c && math.abs(col - c) != row - r

  }
 }

queens(8)

def showa(queens: List[Int]) = {
  val lines =
    for (col <- queens.reverse)
      yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
  "\n" + (lines mkString "\n")
}

queens(4) map showa mkString ("\n")