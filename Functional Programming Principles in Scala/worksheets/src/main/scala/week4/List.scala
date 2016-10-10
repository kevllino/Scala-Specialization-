package week4

/**
  * Created by keid on 02/10/2016.
  */

import java.util.NoSuchElementException

/**
  * Created by keid on 25/09/2016.
  */
trait List[+T] {
  def isEmpty: scala.Boolean
  def head: T
  def tail: List[T]
  def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
}

// val only eval at initialisation and vals are field overriding the abstract method of the trait
class Cons[T](val head: T, val tail: List[T]) extends List[T]{
  def isEmpty = false
}

object Nil extends List[Nothing]{
  def isEmpty: scala.Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object List{
  // List(1, 2)
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, Nil))
  def apply[T](): List[T] = Nil

}