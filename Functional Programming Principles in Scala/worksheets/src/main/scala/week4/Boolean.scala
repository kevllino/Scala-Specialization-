package week4

/**
  * Created by keid on 02/10/2016.
  */
abstract class Boolean {
// if (this.cond) t_expression else e_expression or cond.ifThemElse(te,ee)
def ifThenElse[T](t: =>T, e: => T): T

def && (x: Boolean): Boolean = ifThenElse(x, False) // if this x else false
def  || (x: Boolean): Boolean = ifThenElse(True, x)

  def unary_! : Boolean =  ifThenElse(False, True)


  def == (x: Boolean): Boolean = ifThenElse(x, x.unary_!)
  def != (x: Boolean): Boolean = ifThenElse(x.unary_!, x)

  def < (x: Boolean): Boolean = ifThenElse(False, x) // false < true <=> this.<(x)

}

object True extends Boolean {
  def ifThenElse[T](t: =>T, e: => T) = t
}

object False extends Boolean {
  def ifThenElse[T](t: =>T, e: => T) = e
}