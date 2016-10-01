import week3._

abstract class IntSet{
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet

}

// implement sets as binary trees

object Empty extends IntSet{
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty,  Empty)
  def union(other: IntSet): IntSet = other

  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if(x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int): IntSet =
    if(x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this

  // terminates because each call to union invoves sets smaller than the initial one
  // dynamic method dispatch:
  // code invoked my method call depends on the
  // runtime type of the object that contains the method
  def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem

  override def toString = "{" + left +  elem + right + "}"

}

val t1 = new NonEmpty(3, Empty, Empty)
val t2 = t1 incl 4

def take[T](n: Int, lista: List[T]): T = {
  def loop[T](n: Int, lista: List[T], acc: Int): T = {
    if (n == acc) lista.head
    else if (n > acc) loop(n, lista.tail, acc + 1)
    else throw new IndexOutOfBoundsException

  }
  loop(n, lista, 0)
}

def nth[T](n: Int, xs: List[T]): T = {
  if (xs.isEmpty) throw new IndexOutOfBoundsException
  if(n == 0) xs.head
  else nth(n-1, xs.tail)

}

val lis = new Cons(1, new Cons(2, new Nil))
take(0, lis)