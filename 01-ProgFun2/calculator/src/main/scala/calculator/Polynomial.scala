package calculator

import math.sqrt

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double], c: Signal[Double]): Signal[Double] =
    Signal{
      b() * b() - 4 * a() * c()
    }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = Signal{
      if (delta() == 0 && a() != 0) Set((-b() / 2 * a()))
      else if(delta() > 0 && a() != 0 ) Set( ((-b()+sqrt(delta())) / (2 * a())) , (-b() - sqrt(delta())) / (2 * a()))
      else Set()
  }
}
