package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {
    def loop(chars: Array[Char], acc: Int): Int = {
        if (chars.isEmpty || acc < 0) acc
        else if (chars.head == '(') loop(chars.tail, acc + 1)
        else if (chars.head == ')') loop(chars.tail, acc - 1)
        else loop(chars.tail, acc)
    }
    loop(chars, 0) == 0
  }
    /** Returns `true` iff the parentheses in the input `chars` are balanced.
      */
    def parBalance(chars: Array[Char], threshold: Int): Boolean = {
      def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) = {
        // arg1 = number of left paranthesis
        // arg2 closing paranthesis in the right subtree
        if (idx < until) {
          chars(idx) match {
            case '(' => traverse(idx + 1, until, arg1 + 1, arg2)
            case ')' =>
              if (arg1 > 0) traverse(idx + 1, until, arg1 - 1, arg2)
              else traverse(idx + 1, until, arg1, arg2 + 1)

            case _ => traverse(idx + 1, until, arg1, arg2)
          }
        }
          else (arg1, arg2)
      }


      def reduce(from: Int, until: Int): (Int, Int) = {
        if ((until - from) <= threshold) traverse(from, until, 0, 0)
        else {
          val mid = from +  (until - from) / 2
          val ((a1, a2), (b1, b2)) = parallel(reduce(from, mid), reduce(mid, until))
          if(a1 > b2) ((a1 - b2 + b1), a2)
          else (b1, b2 - a1 + a2)
        }
      }

      reduce(0, chars.length) == (0, 0)
    }

    // For those who want more:
    // Prove that your reduction operator is associative!

}
