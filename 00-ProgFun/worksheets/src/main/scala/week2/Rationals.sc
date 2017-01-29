class Rational(x:Int, y:Int){

  private def gcd(a:Int, b:Int): Int = if(b == 0) a else gcd(b, a%b)
  require(y > 0, "denominator must be non zero")
  private val g = gcd(x,y)
  def num = x // g
  def den = y // g

  // auxiliary constructor
  def this(x: Int) = this(x, 1)

  def + (that: Rational) =
   new Rational( num * that.den + den * that.num, den * that.den)

  // prefix operator different than infix operator so convention
  def unary_- : Rational =  new Rational(- x, y)

  def - (that: Rational): Rational =  this + -that

  def < (that: Rational): Boolean = this.num * that.den < this.den * that.num

  def max(that: Rational) = if(this < that) that else this

  override def toString = num + "/" + den

}


val x = new Rational(1,3)
x.num
val y = new Rational(50,1)
val z = new Rational(3,2)


// new Rational(1,0)

// infix notation: any method with a parameter can be used like an infox operator

x < y
x + y

// precedence of an operator is determined by its first character

// ((a + b) ^? (c ?^ d)) less ((a ==> b) | c)


import scala.math.abs
def isCloseEnough(x: Double, y: Double) =
  abs((x-y)/x) /x < 0.0001

def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  def iterate(guess: Double): Double = {
    // println("guess" + guess)
    val next = f(guess)
    if (isCloseEnough(guess, next)) next
    else iterate(next)
  }
  iterate(firstGuess)
}

def averageDamp(f: Double => Double)(x: Double) = (x+f(x))/2

// sqrt(x) => y * y = x by divinding:
def sqrt(x: Double) = fixedPoint(averageDamp( y => x/y))(1)
sqrt(2)

type Set = Int => Boolean
def contains(s: Set, elem: Int): Boolean = s(elem)

val negativeSet: Set = (x: Int) => x < 0
contains(negativeSet, 3)

def singletonSet(element: Int): Set = (x: Int) => x == element

def union(set1: Set, set2: Set): Set =
  (x: Int) => contains(set1, x) || contains(set2, x)

def intersect(set1: Set, set2: Set): Set =
  (x: Int) => contains(set1, x) && contains(set2, x)

def diff(s: Set,t: Set): Set =
  (x: Int) => contains(s, x) && !contains(t, x)



contains(singletonSet(1), 2)

val settos: Set = (x: Int) => x > -3 && x < -2
val settis: Set = (x: Int) => x > -20 && x < -2

contains(diff(settos, settis), 5/2)


def filter(s: Set, p: Int => Boolean): Set = (x: Int) => contains(intersect(s,p),x)

contains(filter(negativeSet, (x: Int) => x >= -5 && x <= -3), -3)

val bound = 10

def forall(s: Set, p: Int => Boolean): Boolean = {
  def iter(a: Int): Boolean = {
    if ( a == bound ) true
    else if (contains(s, a) && !contains(p, a)) false
    else iter(a + 1)
  }
  iter(-bound)
}


def negate(p: Int => Boolean): Int => Boolean = (x: Int) => !p(x)
def exists(s: Set, p: Int => Boolean): Boolean = !forall(s, negate(p))

//forall(filter(s,p), p)
/*{
  def iter(a: Int): Boolean = {
  if ( a > bound ) false
  else if (contains(s, a) && contains(p, a)) true
  else iter(a + 1)
  }
iter(-bound)
}*/



def frugal = (x: Int) => -x
contains(map(negativeSet, frugal), 2)

val otraSet: Set = (x: Int) => x >= 1 && x < 20
forall(otraSet, negativeSet)
exists(otraSet, (x: Int) => x < 0)

def pos = negate((x: Int) => x < 0)
pos(3)



def map(s: Set, f: Int => Int): Set = (x: Int) => exists(s, (y: Int) => x == f(y))
val positives: Set = (x: Int) => x > 0
// 1,3,4,5,7,1000

def toStringo(s: Set): String = {
  val xs = for (i <- -bound to bound if contains(s, i)) yield i
  xs.mkString("{", ",", "}")
}

/**
  * Prints the contents of a set on the console.
  */
def printSet(s: Set) {
  println(toStringo(s))
}

toStringo(map(positives, (x: Int) => x - 1))
contains(map(positives, (x: Int) => x - 1), 0)
