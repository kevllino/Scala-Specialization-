// Map = collection and data structure

val rom = Map("I" -> 1, "II"-> 2)
rom("I") // function
rom get "I" match {
  case Some(valeur) => valeur
  case None => "missing data"
}

val names = List("Kevin", "Tim", "Johann")
names sortWith (_.length < _.length)
names.sorted
names groupBy (_.head)

class Poly(val terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)
  val terms = terms0 withDefaultValue 0.0
 /* def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
  def adjust(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
     exp -> (coeff + terms(exp))
    }*/

  def + (other: Poly) = new Poly((other.terms foldLeft terms)(addTerm ))
  def addTerm(terms: Map[Int, Double], term: (Int, Double)) ={
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }


  override def toString =
    (for ((exp, coeff) <- terms.toList.sorted.reverse)
      yield coeff + "x^" + exp) mkString " + "

}

val pol1 = new Poly(Map(1->2.0, 3-> 4.0,5 -> 6.2))
val pol2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
pol1 + pol2

// turn Map into total functions


pol1.terms(7)