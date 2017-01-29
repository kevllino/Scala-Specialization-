package recfun

import org.scalatest._
import org.scalatest.Assertions._
import org.junit.runner.RunWith

class PascalSuite extends FunSuite {
  import Main.pascal
    test("pascal: col=0,row=2") {
      assert(pascal(0,2) === 1)
  }

    test("pascal: col=1,row=2") {
      assert(pascal(1,2) === 2)
  }

    test("pascal: col=1,row=3") {
      assert(pascal(1,3) === 3)
  }

  test("Negative Arguments IllegalArgumentException") {
    intercept[java.lang.IllegalArgumentException] {
      assert(pascal(-1, -1) === 0)
    }
  }

}
