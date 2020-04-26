package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.document

object Key{
  val LEF:  Int = 37
  val UP:   Int = 38
  val RIGHT:Int = 39
  val DOWN: Int = 40
}

object KeyListener {
  private var id: Int = 0
  private var keyListener: List[(Int,dom.KeyboardEvent => Unit)] = List()
  document.onkeydown = {(e: dom.KeyboardEvent) => keyListener.foreach(l => l._2(e))}

  def onChange(key: Int)(b: => Unit): Int = {
    val fun: dom.KeyboardEvent => Unit = (e: dom.KeyboardEvent) => if(e.keyCode == key) b
    keyListener = (id,fun)::keyListener
    id += 1// update
    id - 1 // old
  }
  def remove(id: Int): Unit = {
    keyListener = for(l <- keyListener; if l._1 != id) yield l
  }
  def clear(): Unit = {
    keyListener = List()
    id = 0
  }
}
