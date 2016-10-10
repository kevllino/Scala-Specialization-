// fo and fa are equivalent
val fo = (x: Int) => x * x

// eta expansion
val fa = new Function[Int, Int] {
  def apply(x: Int) = x * x
}

// type bound
// <: Int == upper bound of the type parameter S
// S<: T <==> S is a subtype of T
// def assertAllPos[S <: Int](r: S): S

// NonEmpty <: IntSet => List[NonEmpty] <: List[IntSet]
// types for which this relation holds, are called  covariant types
// because subtyping relationship varies with the type parameter

// Liskov substitution principle
// In Scala arrays are not covariant so NonEmpty -> Inset does not imply
// Array[NonEmpty] -> Array[Inset]

// object oriented decomposition
/*
trait Expr{
 def eval: Int

}

class Number(n: Int) extends Expr{
  def eval: Int = n
}

class Sum(e1: Expr, e2: Expr) extends Expr{
  def eval: Int = e1.eval + e2.eval
}
*/

trait Expr{
  def eval: Int = this match{
    case Number(n) => n
    //case Var(x) => x
    case Sum(e1, e2) => e1.eval + e2.eval
    case _ => this.eval
  }

  def show: String = this match{
    case Var(t) => t.toString
    case Number(n) => n.toString
    case Sum(e1, e2) => e1.eval +  " + " +  e2.eval
    case Prod(e1, e2) =>  "( " + e1.eval + ")" +  " * " +  e2.eval

  }

}
case class Number(n: Int) extends Expr
case class Var(t: String) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr


val num1 = new Number(4)
num1.eval
num1.show
Sum(Number(3), Number(9)).show

// Prod(Number(2), Var("x")).show

/*
e match {
  case pattern1 => expr1
  case pattern2 => expr2

}*/

// lists are immutable and recursive
// while arrays are flat
// all operators ending with a ':' are associated to the right

"apple" :: "carrot" :: Nil

def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y,isort(ys))

}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if(x <= y) x :: xs else y :: insert(x, ys)
  // case y :: ys if x <= y  => x :: y :: ys
  // case y :: ys if x > y => y :: x :: ys
}

isort(List(3,2,7,9))

def times(chars: List[Char]): List[(Char, Int)] = {
  def timesAcc(acc: Map[Char, Int],c: Char) = {
    val count = acc.getOrElse(c, 0) + 1
    acc + (c -> count)
  }

  chars.foldLeft(Map[Char, Int]())(timesAcc).toList
}

def times2(chars: List[Char]): List[(Char, Int)] = {
  chars.map(el => (el, 1)).groupBy(_._1).map(el => (el._1, el._2.length)).toList
}

times("ababbac".toList).take(2)

"swqswq".toList contains 'a'
List('a').tail

val al: List[(Char, List[Int])] = ('a', List(1,0)):: Nil
al.filter( _._1 == 'a' ).flatMap(_._2)