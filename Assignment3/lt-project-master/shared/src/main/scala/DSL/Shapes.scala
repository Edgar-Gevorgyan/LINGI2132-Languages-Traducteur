package DSL


sealed trait Shape {
  type A <: Shape
  def and(s: Shape): ComposedShape[Shape] = ComposedShape(List(this, s))
  def +(s: Shape): ComposedShape[Shape] = this.and(s)
  def moveX(v: Int): Unit
  def moveY(v: Int): Unit
  def color(c: String): Unit
  def strokeWidth(sW : Int): Unit
  def change(property: CanvasElementModifier[A]): Unit
  def fill(f: Boolean): Unit
  def attachImage(url: String): Unit
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
  var imageAttached: Boolean = false
  var imageURL: String = ""
  def color(c: String): Unit = color = c
  def strokeWidth(sW: Int): Unit = strokeWidth = sW
  def fill(f: Boolean): Unit = filled = f
  def attachImage(url: String): Unit = {
    imageAttached = true
    imageURL = url

  }
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
  def and(s: ComposedShape[MyType]): ComposedShape[MyType] = ComposedShape(this.l++s.l)
  def +(s: ComposedShape[MyType]): ComposedShape[MyType] = this.and(s)
  def map[OutType <: Shape](f: MyType => OutType): ComposedShape[OutType] = ComposedShape(this.l.map(f))
  def flatMap[OutType <: Shape](f: MyType => Iterable[OutType]): ComposedShape[OutType] = ComposedShape(this.l.flatMap(f))
  def foreach[B](f: MyType => B): Unit = l.foreach(f)
  override def moveX(v: Int): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Int): Unit = this.foreach(s => s.moveY(v))
  override def color(c: String): Unit = this.foreach(s => s.color(c))
  override def strokeWidth(sW: Int): Unit = this.foreach(s => s.strokeWidth(sW))
  override def fill(f: Boolean): Unit = this.foreach(s => s.fill(f))
  override def attachImage(url: String): Unit = this.foreach(s => s.attachImage(url))
  override def change(property: CanvasElementModifier[A]): Unit = this.foreach(s => property.change(s))
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends SingleShape with Ordered[Rectangle]{
  type A = Rectangle
  def and(s: Rectangle): ComposedShape[Rectangle] = ComposedShape(List(this, s))
  def +(s: Rectangle): ComposedShape[Rectangle] = this.and(s)
  def and(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = this.and(s)
  override def compare(that: Rectangle): Int = {
    if (this.x == that.x && this.y == that.y && this.width == that.width){
      this.height - that.height
    } else if (this.x == that.x && this.y == that.y) {
      this.width - that.width
    } else if (this.x == that.x) {
      this.y - that.y
    } else {
      this.x - that.x
    }
  }
}

case class Circle(var x: Int, var y: Int, var radius: Int) extends SingleShape with Ordered[Circle]{
  type A = Circle
  def and(s: Circle): ComposedShape[Circle] = ComposedShape(List(this, s))
  def +(s: Circle): ComposedShape[Circle] = this.and(s)
  def and(s: ComposedShape[Circle]): ComposedShape[Circle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Circle]): ComposedShape[Circle] = this.and(s)
  override def compare(that: Circle): Int = {
    if (this.x == that.x && this.y == that.y){
      this.radius - that.radius
    } else if (this.x == that.x) {
      this.y - that.y
    } else {
      this.x - that.x
    }
  }
}

case class Grid(unit: Int, nb_row: Int, nb_col: Int, color: String, wall: Boolean, wallColor: String){
  var grid: ComposedShape[Rectangle] = ComposedShape(Nil)
  for (i <- 0 until nb_row){
    for (j <-  0 until nb_col){
      val rec = Rectangle(unit * j, unit * i, unit, unit)
      rec change Color(color)
      if (wall && (i == 0 || i == nb_row-1 || j == 0 || j == nb_col-1)) {
        rec change Fill(true)
        rec change Color(wallColor)
      }
      grid = rec + grid
    }
  }
  def addObstacle(unitX: Int, unitY: Int, color: String): Unit ={
    grid = for (rec <- grid) yield
      if (rec == Rectangle(unit * unitX, unit * unitY, unit, unit)){
        rec change Color(color)
        rec change Fill(true)
        rec
      } else {
        rec
      }
  }
  def removeObstacle(unitX: Int, unitY: Int): Unit ={
    grid = for (rec <- grid) yield
      if (rec == Rectangle(unit * unitX, unit * unitY, unit, unit)){
        rec change Color(color)
        rec change Fill(false)
        rec
      } else {
        rec
      }
  }
}


