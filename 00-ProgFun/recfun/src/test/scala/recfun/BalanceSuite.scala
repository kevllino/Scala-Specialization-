package recfun

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance
  import Main.balance_2

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance_2("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance_2("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance_2(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance_2("())(".toList))
  }

  test("balance: '(())' is balanced") {
    assert(balance_2("()".toList))
  }

}
