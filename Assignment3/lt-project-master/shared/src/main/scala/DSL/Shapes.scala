package DSL


sealed trait Shape {
  type A <: Shape
  def and(s: Shape) : ComposedShape[Shape] = ComposedShape(List(this, s))
  def +(s: Shape) : ComposedShape[Shape] = this.and(s)
  def moveX(v: Int): Unit
  def moveY(v: Int): Unit
  def color(c: String) : Unit
  def strokeWidth(sW : Int) : Unit
  def change(property: CanvasElementModifier[A]): Unit
  def fill(f: Boolean): Unit
}
object Shape {
  implicit def ArrayRectangle2ComposedShape(shapes:Array[Rectangle]): ComposedShape[Rectangle] = ComposedShape(shapes.toList)
  implicit def ArrayCircle2ComposedShape(shapes:Array[Circle]): ComposedShape[Circle] = ComposedShape(shapes.toList)
  implicit def IterableRectangle2ComposedShape(shapes:Iterable[Rectangle]): ComposedShape[Rectangle] = ComposedShape(shapes.toList)
  implicit def IterableCircle2ComposedShape(shapes:Iterable[Circle]): ComposedShape[Circle] = ComposedShape(shapes.toList)
}

sealed trait ShapeAttributes {
  var color: String = "red"
  var strokeWidth: Int = 1
  var filled: Boolean = false
  def color(c: String): Unit = color = c
  def strokeWidth(sW: Int): Unit = strokeWidth = sW
  def fill(f: Boolean): Unit = filled = f
}

sealed trait SingleShape extends Shape with ShapeAttributes {
  var x: Int
  var y: Int
  override def moveX(v: Int): Unit = x += v
  override def moveY(v: Int): Unit = y += v
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this.asInstanceOf[A])
}

case class ComposedShape[MyType <: Shape](l: List[MyType]) extends Shape {
  type A = MyType
  def apply(i: Int): MyType = l(i)
  def and(s: ComposedShape[MyType] ) : ComposedShape[MyType] = ComposedShape(this.l++s.l)
  def +(s: ComposedShape[MyType] ) : ComposedShape[MyType] = this.and(s)
  def map[OutType <: Shape](f: MyType => OutType) : ComposedShape[OutType] = ComposedShape(this.l.map(f))
  def flatMap[OutType <: Shape](f: MyType => Iterable[OutType]) : ComposedShape[OutType] = ComposedShape(this.l.flatMap(f))
  def foreach[B](f: MyType => B) : Unit = l.foreach(f)
  override def moveX(v: Int): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Int): Unit = this.foreach(s => s.moveY(v))
  override def color(c: String): Unit = this.foreach(s => s.color(c))
  override def strokeWidth(sW: Int): Unit = this.foreach(s => s.strokeWidth(sW))
  override def fill(f: Boolean): Unit = this.foreach(s => s.fill(f))
  override def change(property: CanvasElementModifier[A]): Unit = this.foreach(s => property.change(s))
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends SingleShape{
  type A = Rectangle
  def and(s: Rectangle) : ComposedShape[Rectangle] = ComposedShape(List(this, s))
  def +(s: Rectangle) : ComposedShape[Rectangle] = this.and(s)
  def and(s: ComposedShape[Rectangle] ) : ComposedShape[Rectangle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Rectangle] ) : ComposedShape[Rectangle] = this.and(s)
}

case class Circle(var x: Int, var y: Int, var radius: Int) extends SingleShape{
  type A = Circle
  def and(s: Circle) : ComposedShape[Circle] = ComposedShape(List(this, s))
  def +(s: Circle) : ComposedShape[Circle] = this.and(s)
  def and(s: ComposedShape[Circle]) : ComposedShape[Circle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Circle]) : ComposedShape[Circle] = this.and(s)
}


