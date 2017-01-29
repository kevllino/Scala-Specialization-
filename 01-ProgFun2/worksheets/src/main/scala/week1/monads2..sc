// A monad is a parametric type M[T] with 2 operations flatMap and unit,
// that have to satisfy some laws:
/*trait M[T] {
  def flatMap[U](f: T => M[U]): M[U]
}*/

// def unit[T](x: T): M[T]  // called bind

// List is a monad unit(x) = List(x)
// Set is a monad with unit(x) = Set(x)
// Option is a monad with unit(x) = Some(x)
// Generator is a monad with unit(x) = single(x)

val m = List(1, 2, 3, 4)

def f(x: Int) = x + 2

m map f
// m flatMap (x => unit(f(x))) // unit <=> map
// == m flatMap (f andThen unit)

// monoid simpler monads e.g. Int

// to qualify as a monad, a type has to satisfy 3 laws
// associativity: (m flatMap f) flatMap g == m flatMap ((x => f(x) flatMap g))
// left unit: unit(x) flatMap f == f(x)
// right unit m flatMap unit == m

abstract class Option[+T]{
  def flatMap[U](f: T => Option[U]): Option[U] = this match {
    case Some(x) => f(x)
    case None => None
  }
}

opt flatMap f flatMap g
== (opt match {case Some(x) => f(x) case None => None })
match {case Some(y) => g(y) case None => None}

opt match {
  case Some(x) => f(x) flatMap g
  case None => None
}

opt flatMap (x=> f(x) flatMap g)