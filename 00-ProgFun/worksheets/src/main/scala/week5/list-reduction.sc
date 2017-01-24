// reduceLeft inserts a givvn binary operator
// between adjacent elements of a list

def sum(xs: List[Int]) = (0 :: xs) reduceLeft ((x,y) => x + y)
//  or
def sum1(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _)

// reduceLeft cannot be applied to Nil
// foldLeft takes an accumulator

def sum3(xs: List[Int]) = (xs foldLeft 0) (_ + _)

// left  or right because lean to the left /right

def concat[T](xs: List[T], ys: List[T]): List[T] =
  (xs foldRight ys)(_ :: _)

def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())( f(_) :: _ )


def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0)( (x, y) => y + 1 )


mapFun(List(1, 2, 4), (x: Int) => x * x)
lengthFun(List(1,2,33))

