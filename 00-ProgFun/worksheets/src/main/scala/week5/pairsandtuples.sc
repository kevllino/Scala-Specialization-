import math.Ordering


def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xsl, y :: ysl) =>
          if (ord.lt(x, y)) x :: merge(xsl, ys)
          else y :: merge(xs, ysl)
      }
    //def merge(xs: List[Int], ys: List[Int]) = xs ::: ys
      val (fst, snd) = xs splitAt n
      merge(msort(fst), msort(snd))
  }
}



List(1,2,3,4) splitAt 2

val pair = ("answer", 42)
val (label, value) = pair
value



val nums = List(2, -4, 5, 7, 1)

msort(nums)


val s = new ::[Int](4,Nil)
