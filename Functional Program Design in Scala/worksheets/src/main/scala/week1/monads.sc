trait Generator[+T]{
  self =>

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
   def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }

}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  def generate = rand.nextInt()
}

val booleans = new Generator[Boolean] {
  def generate = integers.generate > 0
}

val pairs = new Generator[(Int, Int)] {
  def generate = (integers.generate, integers.generate)
}

val booleans2 = for (x <- integers) yield x > 0
val booleans3 = integers map {x => x > 0}


def single[T](x: T): Generator[T] = new Generator[T] {
  def generate = x
}

def emptyList = single(Nil)
def nonEmptyLists = for {
  head <- integers
  tail <- lists
} yield head :: tail

def lists: Generator[List[Int]] = for {
  isEmpty <- booleans
  list <- if(isEmpty) emptyList else nonEmptyLists
} yield list

// Tree Generator
trait Tree
case class Leaf(x: Int) extends Tree
case class Inner(left: Tree, right: Tree) extends Tree

def leafs: Generator[Leaf] = for {
  x <- integers
} yield Leaf(x)

def inners: Generator[Inner] = for {
  l <- trees
  r <- trees
} yield Inner(l, r)

def trees: Generator[Tree] = for {
  isLeaf <- booleans
  tree <- if(isLeaf) leafs else inners
} yield tree

// partial function from Any to String
val pf: PartialFunction[Any, String] = {
  case i: Int => "Number"
  case s: String => "string "
}
pf(2)

for (i <- (1 to 5).par ) yield for (j <- 1 to 4) yield (i, j)


def myWhile(condition: => Boolean)(body: => Unit): Unit ={
  if (condition) {
    body
    myWhile(condition)(body)
  }
}

var i = 0
myWhile(i < 5){
  println(i)
  i += 1
}
