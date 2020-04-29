package DSL


sealed trait Shape {
  type A <: Shape
  def and(s: Shape) : ComposedShape[Shape] = ComposedShape(List(this, s))
  def +(s: Shape) : ComposedShape[Shape] = this.and(s)
  def moveX(v: Double): Unit
  def moveY(v: Double): Unit
  def moveXto(v: Double): Unit
  def moveYto(v: Double): Unit
  def color(c: String) : Unit
  def strokeColor(sC: String) : Unit
  def strokeWidth(sW : Int) : Unit // TODO a verifier si double ou int
  def change(property: CanvasElementModifier[A]): Unit
  def fill(f: Boolean): Unit
  def shadowOffsetX(sO: Double): Unit
  def shadowOffsetY(sO: Double): Unit
  def shadowBlur(sB: Double): Unit
  def shadowColor(sC: String): Unit
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
  var strokeColor: String = "#000"
  var strokeWidth: Int = 1
  var filled: Boolean = false
  var shadowOffsetX : Double = 0
  var shadowOffsetY : Double = 0
  var shadowBlur : Double = 0
  var shadowColor : String = "#000000"
  var imageAttached: Boolean = false
  var imageURL: String = ""
  def color(c: String): Unit = color = c
  def strokeColor(sC: String) : Unit = this.strokeColor = sC
  def strokeWidth(sW: Int): Unit = strokeWidth = sW
  def fill(f: Boolean): Unit = filled = f
  def shadowOffsetX(sO: Double): Unit = shadowOffsetX = sO
  def shadowOffsetY(sO: Double): Unit = shadowOffsetY = sO
  def shadowBlur(sB: Double): Unit = shadowBlur = sB
  def shadowColor(sC: String): Unit = shadowColor = sC
  def attachImage(url: String): Unit = {
    imageAttached = true
    imageURL = url

  }
}

sealed trait SingleShape extends Shape with ShapeAttributes { // TODO implem Ordered[SingleShape] selon les x y
  var x: Double
  var y: Double
  override def moveX(v: Double): Unit = x += v
  override def moveY(v: Double): Unit = y += v
  override def moveXto(v: Double): Unit = x = v
  override def moveYto(v: Double): Unit = y = v
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this.asInstanceOf[A])
}

case class ComposedShape[MyType <: Shape](var l: List[MyType]) extends Shape { // TODO regler couille du +
  type A = MyType
  def apply(i: Int): MyType = l(i)
  def and(s: ComposedShape[MyType] ) : ComposedShape[MyType] = ComposedShape(this.l++s.l)
  def +(s: ComposedShape[MyType] ) : ComposedShape[MyType] = this.and(s)
  def map[OutType <: Shape](f: MyType => OutType) : ComposedShape[OutType] = ComposedShape(this.l.map(f))
  def flatMap[OutType <: Shape](f: MyType => Iterable[OutType]) : ComposedShape[OutType] = ComposedShape(this.l.flatMap(f))
  def foreach[B](f: MyType => B) : Unit = l.foreach(f)
  override def moveX(v: Double): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Double): Unit = this.foreach(s => s.moveY(v))
  def moveXto(v: Double): Unit = this.foreach(s => s.moveXto(v))
  def moveYto(v: Double): Unit = this.foreach(s => s.moveYto(v))
  override def color(c: String): Unit = this.foreach(s => s.color(c))
  override def strokeWidth(sW: Int): Unit = this.foreach(s => s.strokeWidth(sW))
  override def strokeColor(sS: String): Unit = this.foreach(s => s.strokeColor(sS))
  override def fill(f: Boolean): Unit = this.foreach(s => s.fill(f))
  def shadowOffsetX(sO: Double): Unit = this.foreach(s => s.shadowOffsetX(sO))
  def shadowOffsetY(sO: Double): Unit = this.foreach(s => s.shadowOffsetY(sO))
  def shadowBlur(sB: Double): Unit = this.foreach(s => s.shadowBlur(sB))
  def shadowColor(sC: String): Unit = this.foreach(s => s.shadowColor(sC))
  override def attachImage(url: String): Unit = this.foreach(s => s.attachImage(url))
  override def change(property: CanvasElementModifier[A]): Unit = this.foreach(s => property.change(s))
}

case class Rectangle(var x: Double, var y: Double, var width: Double, var height: Double) extends SingleShape with Ordered[Rectangle]{
  type A = Rectangle
  def and(s: Rectangle): ComposedShape[Rectangle] = ComposedShape(List(this, s))
  def +(s: Rectangle): ComposedShape[Rectangle] = this.and(s)
  def and(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = this.and(s)
  override def compare(that: Rectangle): Int = {
    if (this.x == that.x && this.y == that.y && this.width == that.width){
      (this.height - that.height).asInstanceOf[Int]
    } else if (this.x == that.x && this.y == that.y) {
      (this.width - that.width).asInstanceOf[Int]
    } else if (this.x == that.x) {
      (this.y - that.y).asInstanceOf[Int]
    } else {
      (this.x - that.x).asInstanceOf[Int]
    }
  }
}

case class Circle(var x: Double, var y: Double, var radius: Double) extends SingleShape with Ordered[Circle]{
  type A = Circle
  def and(s: Circle): ComposedShape[Circle] = ComposedShape(List(this, s))
  def +(s: Circle): ComposedShape[Circle] = this.and(s)
  def and(s: ComposedShape[Circle]): ComposedShape[Circle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Circle]): ComposedShape[Circle] = this.and(s)
  override def compare(that: Circle): Int = {
    if (this.x == that.x && this.y == that.y){
      (this.radius - that.radius).asInstanceOf[Int]
    } else if (this.x == that.x) {
      (this.y - that.y).asInstanceOf[Int]
    } else {
      (this.x - that.x).asInstanceOf[Int]
    }
  }
}

case class Text(var x: Double, var y: Double, var txt: String) extends SingleShape{
  type A = Text
  var font: String = "sans-serif"
  var fontSize: Double = 10
  var textAlign: String = "start"
  var textBaseline: String = "alphabetic"

  def font(font: String): Unit = this.font = font
  def fontSize(fontSize: Double): Unit = this.fontSize = fontSize
  def textAlign(textAlign: String): Unit = this.textAlign = textAlign
  def textBaseline(textBaseline: String): Unit = this.textBaseline = textBaseline
}

class Grid(nb_row: Int, nb_col: Int,color: String, wall: Boolean, wallColor: String) extends ComposedShape[Rectangle](Grid.build_grid(nb_row, nb_col,color, wall, wallColor)){
  def fillGridCase(unitX: Int, unitY: Int, color: String): Unit ={
    l = for(rec <- l) yield
      if(rec == Rectangle(unitX,unitY,1,1)){
        rec change Color(color)
        rec change Fill(true)
        rec
      } else {
        rec
      }
  }
  def unFillGridCase(unitX: Int, unitY: Int): Unit ={
    l = for(rec <- l) yield
      if(rec == Rectangle(unitX,unitY,1,1)){
        rec change Color(color)
        rec change Fill(false)
        rec
      } else {
        rec
      }
  }
}

object Grid{
  def apply(nb_row: Int, nb_col: Int, color: String, wall: Boolean, wallColor: String): Grid = new Grid(nb_row, nb_col, color, wall, wallColor)

  def build_grid(nb_row: Int, nb_col: Int,color: String, wall: Boolean, wallColor: String): List[Rectangle] = {
    var grid: List[Rectangle] = Nil
    for(i <- 0 until nb_row){
      for(j <-  0 until nb_col){
        val rec = Rectangle(j,i,1,1)
        rec change Color(color)
        if (wall && (i == 0 || i == nb_row-1 || j == 0 || j == nb_col-1)) {
          rec change Fill(true)
          rec change Color(wallColor)
        }
        grid = rec :: grid
      }
    }
    grid
  }
}


