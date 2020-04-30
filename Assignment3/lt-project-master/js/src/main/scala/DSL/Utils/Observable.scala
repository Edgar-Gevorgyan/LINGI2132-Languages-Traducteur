package DSL.Utils

import DSL._
import org.scalajs.dom
import org.scalajs.dom.document

object Key{
  val A:    Int = 65
  val B:    Int = 66
  val C:    Int = 67
  val D:    Int = 68
  val E:    Int = 69
  val F:    Int = 70
  val G:    Int = 71
  val H:    Int = 72
  val I:    Int = 73
  val J:    Int = 74
  val K:    Int = 75
  val L:    Int = 76
  val M:    Int = 77
  val N:    Int = 78
  val O:    Int = 79
  val P:    Int = 80
  val Q:    Int = 81
  val R:    Int = 82
  val S:    Int = 83
  val T:    Int = 84
  val U:    Int = 85
  val V:    Int = 86
  val W:    Int = 87
  val X:    Int = 88
  val Y:    Int = 89
  val Z:    Int = 90
  val LEFT: Int = 37
  val UP:   Int = 38
  val RIGHT:Int = 39
  val DOWN: Int = 40
  val SPACE:Int = 32
}

object KeyListener {
  private var id: Int = 0
  private var keyListener: List[(Int, dom.KeyboardEvent => Unit)] = List()
  document.onkeydown = {(e: dom.KeyboardEvent) => keyListener.foreach(l => l._2(e))}

  def onChange(key: Int)(b: => Unit): Int = {
    val fun: dom.KeyboardEvent => Unit = (e: dom.KeyboardEvent) => if(e.keyCode == key) b
    keyListener = (id, fun) :: keyListener
    id += 1 // update
    id - 1 // old
  }
  def remove(id: Int): Unit = {
    keyListener = for (l <- keyListener; if l._1 != id) yield l
  }
  def clear(): Unit = {
    keyListener = List()
    id = 0
  }
}

object MouseListener {
  private var id: Int = 0
  private var mouseListener: List[(Int, dom.MouseEvent => Unit)] = List()
  document.onmousedown = {(e: dom.MouseEvent) => mouseListener.foreach(l => l._2(e))}

  def onChange(b: (Double, Double) => Unit): Int = {
    val fun: dom.MouseEvent => Unit = (e: dom.MouseEvent) => b(e.clientX,e.clientY)
    mouseListener = (id, fun) :: mouseListener
    id += 1 // update
    id - 1 // old
  }

  def onChangeInside(shape: Shape, unit: Int = 1)(b: => Unit): Int = {
    val fun: dom.MouseEvent => Unit = (e: dom.MouseEvent) => if(shape.inside(e.clientX/unit, e.clientY/unit)) b
    mouseListener = (id, fun) :: mouseListener
    id += 1 // update
    id - 1 // old
  }

  def remove(id: Int): Unit = {
    mouseListener = for (l <- mouseListener; if l._1 != id) yield l
  }
  def clear(): Unit = {
    mouseListener = List()
    id = 0
  }
}
