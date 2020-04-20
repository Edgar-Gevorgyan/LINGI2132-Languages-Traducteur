package DSLDemo


sealed trait Shape {
  def and(s: Shape) : ComposedShape = ComposedShape(List(this, s))
  def +(s: Shape) : ComposedShape = this.and(s)
  def change(property: CanvasElementModifier[Shape]): Unit
  def moveX(v: Int): Unit
  def moveY(v: Int): Unit
  def color(c: String) : Unit
  def strokeWidth(sW : Int) : Unit
}
object Shape {
  implicit def ArrayRectangle2ComposedShape(shapes:Array[Rectangle]): ComposedShape = ComposedShape(shapes.toList)
  implicit def ArrayCircle2ComposedShape(shapes:Array[Circle]): ComposedShape = ComposedShape(shapes.toList)
  implicit def Iterable2ComposedShape(shapes: List[Shape]): ComposedShape = ComposedShape(shapes)
}

sealed trait ShapeAttributes {
  var color: String = "red"
  var strokeWidth: Int = 1
  def color(c: String): Unit = color = c
  def strokeWidth(sW: Int): Unit = strokeWidth = sW
}

sealed trait SingleShape extends Shape with ShapeAttributes {
  var x: Int
  var y: Int
  override def moveX(v: Int): Unit = x += v
  override def moveY(v: Int): Unit = y += v
  override def change(property: CanvasElementModifier[Shape]): Unit = property.change(this)
}

case class ComposedShape(shapes: List[Shape]) extends Shape {
  def ++(cs: ComposedShape) : ComposedShape = ComposedShape(this.shapes ++ cs.shapes)
  def map(f: Shape => Shape) : ComposedShape = ComposedShape(this.shapes.map(f))
  def flatMap(f: Shape => IterableOnce[Shape]) : ComposedShape = ComposedShape(this.shapes.flatMap(f))
  def foreach[B](f: Shape => B) : Unit = shapes.foreach(f)
  def apply(i: Int): Shape = shapes(i)
  override def moveX(v: Int): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Int): Unit = this.foreach(s => s.moveY(v))
  override def change(property: CanvasElementModifier[Shape]): Unit = this.foreach(s => property.change(s))
  override def color(c: String): Unit = this.foreach(s => s.color(c))
  override def strokeWidth(sW: Int): Unit = this.foreach(s => s.strokeWidth(sW))
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends SingleShape

case class Circle(var x: Int, var y: Int, var radius: Int) extends SingleShape
