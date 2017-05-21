import observatory.Location

val l1 = List(1,2,3,4,5)
l1.foldLeft(0)( (acc, currNum) => {
  println(s"acc = $acc and currNum = $currNum")
  acc + currNum
})