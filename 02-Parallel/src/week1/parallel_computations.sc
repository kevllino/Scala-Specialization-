/*
import com.sun.javafx.tk.Toolkit.Task
import common.task
import common.parallel


def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Double =
  if (a.isEmpty) 0
  else if (s ==  t - 1) math.pow(math.pow(math.abs(a(t - 1)), p), 1 / p)
  else math.pow(math.pow(math.abs(a(s)), p)  + sumSegment(a, p, s + 1, t), 1 /p)


sumSegment(Array(1, 3), 2,0, 2)


def power(x: Int, p: Double) : Int = math.exp(p * math.log(math.abs(x))).toInt

def sumSegment_2(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  var i = s; var sum: Int = 0
  while(i < t){
    sum = sum + power(a(i), p)
    i = i + 1
  }
  sum
}

def pNorm(a: Array[Int], p: Double): Int =
  power(sumSegment_2(a, p, 0, a.length), 1/p)



def pNormTwoPart(a: Array[Int], p: Double): Int = {
  val m = a.length / 2
  val (sum1, sum2) = parallel(sumSegment_2(a, p, 0, m), sumSegment_2(a, p, m, a.length))
  power(sum1 + sum2, 1 / p
  )
}


def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Double =
  if(t - s < 10) sumSegment(a, p, s, t)
  else {
    val m = s + (t - s) / 2
    val (sum1, sum2) = parallel(segmentRec(a, p, s, m), segmentRec(a, p, m, t))
    sum1 + sum2
  }
*/

val r1 = (0 to 1920 by 60).toList
val r2 = r1.tail

r1 zip r2
