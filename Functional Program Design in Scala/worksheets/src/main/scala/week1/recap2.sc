import scala.util.Try

//abstract class List[T]{
//
//def map[U](f: T => U): List[U] = this match {
//  case Nil => Nil
//  case x :: xs => f(x) :: xs.map(f)
//}
//
//  def flatMap[U](f: T => List[U]): List[U] = this match {
//    case Nil => Nil
//    case x :: xs => f(x) ++ xs.flatMap(f)
//  }
//
//
//}

// pat <- expr
// <=> x <- expr withFilter{
// case pat => true
// case _ => false
// } map {
// case pat => x
// }

for {
  x <- 2 to 5
  y <- 2 to x
  if (x%y==0)
} yield (x, y)

(2 to 5) flatMap (
  x => (2 to x) withFilter(
    y => x % y == 0) map (y => (x, y)
    )
  )

// for queries
case class Book(title: String, authors:List[String])
Try(1 == 2)