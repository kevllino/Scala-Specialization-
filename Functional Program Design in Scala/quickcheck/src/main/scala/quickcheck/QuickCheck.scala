package quickcheck

import org.scalacheck.Prop._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    n <- Arbitrary.arbitrary[A]
    h <- Gen.frequency((1, empty), (3, genHeap))
  } yield insert(n, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)


  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { a: Int =>
    val h = insert(a, empty)
    isEmpty(deleteMin(h))
  }

  property("min3") = forAll { (a: Int, b: Int) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)
    if (a < b) (findMin(h2) == a) else (findMin(h2) == b)
  }

  // Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima.
  // (Hint: recursion and helper functions are your friends.)
  property("sorted") = forAll { (h: H) =>
    def isSorted(h: H): Boolean = {
      if(isEmpty(h)) true
      else {
        val previousMin = findMin(h)
        val newH = deleteMin(h)
        isEmpty(newH) || (previousMin <= findMin(newH) && isSorted(newH))
      }
    }
    isSorted(h)
  }


  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("meld1") = forAll { (h1: H, h2: H) =>
    val meldMin = findMin(meld(h1, h2))
    (meldMin == findMin(h1)) || (meldMin == findMin(h2))
  }

  property("meld2") = forAll { (h1: H, h2: H) =>
    def heapEqual (h1: H, h2: H): Boolean =
      if (isEmpty(h1) && isEmpty(h2)) true
      else {
        val min1 = findMin(h1)
        val min2 = findMin(h2)
        min1 == min2 && heapEqual(deleteMin(h1), deleteMin(h2))
      }
    heapEqual(meld(h1, h2), meld(deleteMin(h1), insert(findMin(h1), h2)))
  }
}
