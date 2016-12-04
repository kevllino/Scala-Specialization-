import org.scalameter._

val time = measure {
  for (i <- 0 until 100000) yield i
}
println(s"Total time: $time")