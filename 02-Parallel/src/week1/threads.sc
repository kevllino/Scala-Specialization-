import week1.Account

class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello Kivani")
    println("Bye Kivani")
  }
}

val t = new HelloThread()
val j = new HelloThread()
t.start()
t.join()


j.start()
j.join() // block execution of main thread
         // unitl HelloWorld completes

// synchronized block
private val x = new AnyRef {}
private var uidCount = 0L
def getUniqueId(): Long =  x.synchronized {
  uidCount = uidCount + 1
  uidCount
}

def startThread() = {
  val t = new Thread{
    override def run() = {
      val uids = for (i <- 0 until 10) yield getUniqueId()
      println(uids)
    }
  }
  t.start()
  t
}

startThread(); startThread()


// deadlock
def startThread(a: Account, b: Account, n: Int): Thread = {
  val t = new Thread{
    override def run(): Unit = {
      for (i <- 0 until n) {
        a.transfer(b, 1)
      }
    }
  }
  t.start()
  t
}

val a1 = new Account(5000)
val a2 = new Account(7000)

val p = startThread(a1, a2, 1500)
val s = startThread(a2, a1, 15000)
p.join()
s.join()



