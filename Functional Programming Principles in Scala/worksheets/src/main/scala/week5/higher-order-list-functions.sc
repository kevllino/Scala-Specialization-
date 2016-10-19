// higher order list functions
/*abstract class List[T]{
  def map[U](f: T => U): List[U] = this match {
    case Nil => this
    case y :: ys => f(y) :: ys.map(f)

  }
}*/

def squareList(xs: List[Int]): List[Int] =
  xs match {
    case Nil => Nil
    case y :: ys => y * y :: squareList(ys)
  }

def squareList2(xs: List[Int]): List[Int] =
  xs map (x => x * x)

/*def filter[T](p: T => Boolean): List[T] = this match {
  case Nil => this
  case y :: ys => if (p(y)) cy :: ys.filter(p) else ys.filter(p)

}*/


val nums = List(2, -4, 5, 7, 1)
val nums2 = List(2, 2, 2, 3, 1)
nums2 takeWhile  (_ == 2)

nums filter (x => x > 0)
nums filterNot (x => x > 0)
nums partition (x => x > 0 ) // 1 traversal vs 2
nums takeWhile (x => x > 0) // longest prefix of list
nums dropWhile (x => x > 0)
nums span (x => x > 0)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 => xs1 span  (_ == x) match {
    case (fst, snd) => List(List(x) ::: fst) ++ pack(snd)
  }
}

pack(List("a", "a", "a", "b", "c", "c", "a"))

def packM[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (fst, snd) = xs span (_ == x)
    fst :: packM(snd)
}

def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs) map (x  => (x.head, x.length))

encode(List("a", "a", "a", "b", "c", "c", "a"))