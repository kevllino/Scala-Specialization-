def partition(pivot: Int, rest : List[Int], smaller: List[Int], equal: List[Int],larger: List[Int]): (List[Int], List[Int],List[Int]) = {
  rest match {
    case Nil =>  (smaller, equal, larger)
    case h :: t if h == pivot => partition(pivot, t,  h :: smaller, h :: equal,larger)
    case h :: t if h <= pivot => partition(pivot, t,  h :: smaller, equal,larger)
    case h :: t if h > pivot => partition(pivot, t, smaller, equal,h :: larger)

  }

}
def quicksort(lista: List[Int]): List[Int] = {
  lista match {
    case Nil => List()
    case  pivot :: rest => {
      val (smaller, equal,larger):  (List[Int], List[Int],List[Int]) = partition(pivot, rest,List(), List(),List())
      quicksort(smaller) ++ List(pivot) ++ equal ++ quicksort(larger)
    }
  }
}

quicksort(List(6,4,3,2,6,11))