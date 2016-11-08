
// Stream is like list but tail evaluated on demand
val xs = Stream.cons(1, Stream.cons(2, Stream.empty))


// or by a factory:


(1 to 1000).toStream


def streamRange(lo: Int, hi: Int): Stream[Int] = {
  print(lo + "")
  if (lo >= hi) Stream.empty
  else Stream.cons(lo, streamRange(lo + 1, hi))
}


streamRange(1, 5)

val x1 = 5
x1 #:: xs

//trait Streamo[+A] extends Seq[A] {
//  def isEmpty: Boolean
//  def head: A
//  def tail: Streamo[A]
//
//}

/*
object Streamo{
  def cons[T](hd: T, tl: => Streamo[T]) = new Streamo[T] {
    def isEmpty = false
    def head = hd
    def tail = tl
  }
  val empty = new Streamo[Nothing] {
    def isEmpty = true
    def head = throw new NoSuchElementException("empty.head")
    def tail = throw new NoSuchElementException("empty.tail")
  }


}*/

streamRange(1, 10).take(3)


Stream(1, 2, 3,4).filter(_ % 2 == 0).toSet