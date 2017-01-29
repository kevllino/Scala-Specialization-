// avoid unecessary and repeated computation
// def is reevaluated everytime, lazy val reused except 1st time


def expr = {
  val x = {
    print("x")
    1
  }
  lazy val y = {
    print("y")
    2
  }

  def z = {
    print("z")
    3
  }
  z + y + x + z + y + x

}

expr

// infinite streams
def from(n: Int): Stream[Int] = n #:: from(n + 1)

val nat = from(0)
val m4s = nat map (_ * 4)
(m4s take 100).toList

def sieve(s: Stream[Int]): Stream[Int] =
s.head #:: sieve(s.tail filter (_ % s.head != 0) )

val primes = sieve(from(2))
primes.take(100).toList

