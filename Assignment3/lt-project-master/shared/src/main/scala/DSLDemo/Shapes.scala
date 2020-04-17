package DSLDemo
import ComposedShapeImplicits._


sealed trait Shape {
  def and(s: Shape) : ComposedShape = new ComposedShape(List(this, s))
  def ++(s: Shape) : ComposedShape = this.and(s)
  def change(property: CanvasElementModifier[Shape]): Unit = property.change(this)
  def moveX(v: Int): Unit
  def moveY(v: Int): Unit
  def colorShape(c: String) : Unit
}

sealed trait ShapeAttributes {
  var color: String = "red"
  var strokeWidth: Int = 1
  // Add more attributes here
  var posX: Int = 0
  var posY: Int = 0
  def color(c:String):Unit = color = c
}

object ComposedShapeImplicits {
  implicit def List2ComposedShape(shapes: List[Shape]):ComposedShape = new ComposedShape(shapes)
}

case class ComposedShape(shapes: List[Shape]) extends Shape {
  def map(f: Shape => Shape) : ComposedShape = new ComposedShape(this.shapes.map(f))
  def flatMap(f: Shape => IterableOnce[Shape]) : ComposedShape = new ComposedShape(this.shapes.flatMap(f))
  def foreach[B](f: Shape => B) : Unit = shapes.foreach(f)
  def apply(i: Int): Shape = shapes(i)
  override def moveX(v: Int): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Int): Unit = this.foreach(s => s.moveY(v))
  override def colorShape(c: String): Unit = this.foreach(s => s.colorShape(c))
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends ShapeAttributes with Shape {
  override def moveX(v: Int): Unit = x += v
  override def moveY(v: Int): Unit = y += v
  override def colorShape(c: String): Unit = color = c
}

case class Circle(var x: Int, var y: Int, var radius: Int) extends Shape with ShapeAttributes {
  override def moveX(v: Int): Unit = x += v
  override def moveY(v: Int): Unit = y += v
  override def colorShape(c: String): Unit = color = c
}
