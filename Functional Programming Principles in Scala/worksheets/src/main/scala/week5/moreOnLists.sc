val alo = List(1,2,3,2)
alo.drop(1)

// concatenation
alo ++ List(1,2,9)


alo updated (1,9)

// first occurence or -1
alo indexOf(2)

// <=> alo indexOf 3 >= 0
alo contains 3

def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}


def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

/*def concat[T](xs: List[T], ys: List[T]) = xs match {
  case List() => ys
  case z :: zs => List(z) :: concat(zs, ys)
}*/


def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y :: ys => reverse(ys) ++ List(y)

}

def removeAt[T](n: Int, xs: List[T]) =
  if (n > xs.length) xs
  else (xs take n) ::: (xs drop n + 1)


List(1,2) ::: List(3,4)

def removeAt1[T](n: Int, xs: List[T]) =
  if(n>xs.length) xs
  else xs.take(n) ::: xs.drop(n+1)

removeAt1(2,List(1,2,3,9))

def flatten(xs: List[Any]): List[Any] = xs match {
  case List() => List()
  case y :: ys => y match {
    case h: List[Any] => h ::: flatten(ys)
    case i => List(i) ::: flatten(ys)
  }
}

flatten(List(List(2,3), List(4,5),2, List(0)))


