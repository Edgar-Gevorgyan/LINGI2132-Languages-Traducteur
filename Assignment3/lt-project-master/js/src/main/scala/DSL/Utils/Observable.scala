package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.document

object Key{
  val LEF:  Int = 37
  val UP:   Int = 38
  val RIGHT:Int = 39
  val DOWN: Int = 40
}

class KeyListener {
  private var keyListener: List[dom.KeyboardEvent => Unit] = List()
  document.onkeydown = {(e: dom.KeyboardEvent) => keyListener.foreach(l => l(e))}

  def onChange(key: Int)(b: => Unit): Unit = {
    val fun: dom.KeyboardEvent => Unit = (e: dom.KeyboardEvent) => if(e.keyCode == key) b
    keyListener = fun::keyListener
  }
}
object KeyListener{
  def apply(): KeyListener = new KeyListener
}
