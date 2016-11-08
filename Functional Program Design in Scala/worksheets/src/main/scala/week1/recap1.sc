println("JSON")
abstract class JSON
case class JSeq(elems: List[JSON]) extends JSON
case class JObj(bindings: Map[String, JSON]) extends JSON
case class JNum(num: Double) extends JSON
case class JStr(str: String) extends JSON
case class JBool(b: Boolean) extends JSON
case object JNull extends JSON

// example
val data = JObj(
  Map(
    "firstName" -> JStr("Kevin"),
    "address" -> JObj(
      Map(
        "streetAdress" -> JStr("scala str"),
        "number" -> JNum(19),
        "city" -> JStr("Lima")
      )
    ),
    "phoneNumbers" -> JSeq(
      List(
        JObj(
          Map(
            "type" -> JStr("home"), "number" -> JStr("212 555-1234")
          )
        ),
        JObj(
          Map(
            "type" -> JStr("work"), "number" -> JStr("222 555-1234")
          )
        )
      )
    )
  )
)
val mappa = "a" -> 2

trait Function1[-A, +R]{
  def apply(x: A): R
}

type JBinding = (String, JSON)

def show(json: JSON): String = json match {

  case JSeq(elems) =>
    "[\n" +  (elems map show mkString ", " ) + "\n]"
  case JObj(b) =>
  val assocs = b map {
    case (key, value) =>
      "\""+ key +"\":" + show(value)
  }
    "\n{\n" + (assocs mkString(", \n" )) +"\n}"

  case JNum(n) =>
    n.toString
  case JStr(s) =>
    "\"" + s + "\""
  case JBool(b) =>
    b.toString
  case JNull =>
    "null"
}

show(data)


val f: PartialFunction[String, String] = {
  case "ping" => "pong"
  // case _ => "other"
}
f("ping")
f.isDefinedAt("p")