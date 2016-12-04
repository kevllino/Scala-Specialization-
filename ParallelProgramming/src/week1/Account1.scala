package week1

/**
  * Created by keid on 03/12/2016.
  */
class Account1(private var amount: Double = 0L) {

  // resolving deadlock by ordering resource
  private val x = new AnyRef {}
  private var uidCount = 0L
  def getUniqueId(): Long =  x.synchronized {
    uidCount = uidCount + 1
    uidCount
  }

  val uid = getUniqueId()
  private def lockAndTransfer(target: Account1, n: Double) =
    this.synchronized{
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }

  def transfer(target: Account1, n: Double) =
    if (this.uid < target.uid) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n)

}
