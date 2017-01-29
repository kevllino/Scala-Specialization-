def sum(f: Int => Int)(a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop( a + 1, acc + f(a))
  }
  loop(a, 0)
}

sum(x => x* x)(1,3)

// currying functions <=> functions returning functions
def sum_curried(f: Int => Int)(a: Int, b: Int): Int = {
  if(a> b) 0 else sum(f)(a+1, b)
}

def square(x: Int): Int = x * x
sum_curried(square)(1,2)


def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a+1, b)
}

product( (x: Int) => x * x)(3, 7)


def factorial(n: Int): Int = product((x: Int) => x)(1, n)


factorial(3)


def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  if(a > b) zero
  else  combine(f(a), mapReduce(f, combine, zero)(a+1, b))
}



def product_gen(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b )

product_gen(x => x)(1,3)