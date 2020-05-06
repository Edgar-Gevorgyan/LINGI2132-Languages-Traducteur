package DSL


/**
 * Shape trait contains all function can be applied to a shape
 */
sealed trait Shape {
  type A <: Shape
  def and(s: Shape) : ComposedShape[Shape] = ComposedShape(List(this, s))
  def +(s: Shape) : ComposedShape[Shape] = this.and(s)
  def moveX(v: Double): Unit
  def moveY(v: Double): Unit
  def move(x: Double)(y: Double): Unit
  def moveXto(v: Double): Unit
  def moveYto(v: Double): Unit
  def moveTo(x: Double)(y: Double): Unit
  def color(c: String) : Unit
  def strokeColor(sC: String) : Unit
  def strokeWidth(sW : Double) : Unit
  def change(property: CanvasElementModifier[A]): Unit
  def fill(f: Boolean): Unit
  def shadowOffsetX(sO: Double): Unit
  def shadowOffsetY(sO: Double): Unit
  def shadowBlur(sB: Double): Unit
  def shadowColor(sC: String): Unit
  def attachImage(url: String): Unit
  def inside(x: Double)(y: Double): Boolean = false
}

/**
 * contains all implicit conversion
 * an iterable or an array of type X can be converted to a ComposedShape of type X
 */
object Shape {
  implicit def ArrayRectangle2ComposedShape(shapes:Array[Rectangle]): ComposedShape[Rectangle] = ComposedShape(shapes.toList)
  implicit def ArraySquare2ComposedShape(shapes:Array[Square]): ComposedShape[Square] = ComposedShape(shapes.toList)
  implicit def ArrayCircle2ComposedShape(shapes:Array[Circle]): ComposedShape[Circle] = ComposedShape(shapes.toList)
  implicit def ArrayText2ComposedShape(shapes:Array[Text]): ComposedShape[Text] = ComposedShape(shapes.toList)
  implicit def IterableRectangle2ComposedShape(shapes:Iterable[Rectangle]): ComposedShape[Rectangle] = ComposedShape(shapes.toList)
  implicit def IterableSquare2ComposedShape(shapes:Iterable[Square]): ComposedShape[Square] = ComposedShape(shapes.toList)
  implicit def IterableCircle2ComposedShape(shapes:Iterable[Circle]): ComposedShape[Circle] = ComposedShape(shapes.toList)
  implicit def IterableText2ComposedShape(shapes:Iterable[Text]): ComposedShape[Text] = ComposedShape(shapes.toList)
}

/**
 * ShapeAttributes traits contains all attributes associated to a shape and function to modify these
 */
sealed trait ShapeAttributes {
  var color: String = "yellow"
  var strokeColor: String = "#000000"
  var strokeWidth: Double = 1
  var filled: Boolean = false
  var shadowOffsetX: Double = 0
  var shadowOffsetY: Double = 0
  var shadowBlur: Double = 0
  var shadowColor: String = "#000000"
  var imageAttached: Boolean = false
  var imageURL: String = ""
  def color(c: String): Unit = color = c
  def strokeColor(sC: String): Unit = this.strokeColor = sC
  def strokeWidth(sW: Double): Unit = strokeWidth = sW
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

/**
 * SingleShape trait representing a single Shape
 */
sealed trait SingleShape extends Shape with ShapeAttributes {
  var x: Double
  var y: Double
  override def moveX(v: Double): Unit = x += v
  override def moveY(v: Double): Unit = y += v
  override def move(xNew: Double)(yNew: Double): Unit = {
    x += xNew
    y += yNew
  }
  override def moveXto(v: Double): Unit = x = v
  override def moveYto(v: Double): Unit = y = v
  override def moveTo(xNew: Double)(yNew: Double): Unit = {
    x = xNew
    y = yNew
  }
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this.asInstanceOf[A])
}

/**
 * an ComposedShape class representing an several Shape grouped
 * @param l the list containing several Shape grouped
 * @tparam MyType the type of the shapes inside the ComposedShape
 *                if all shape has the same type X the MyType is equals to X
 *                otherwise MyType is equals to shape
 */
case class ComposedShape[MyType <: Shape](var l: List[MyType]) extends Shape {
  type A = MyType
  def apply(i: Int): MyType = l(i)
  def and(s: ComposedShape[MyType]): ComposedShape[MyType] = ComposedShape(this.l ++ s.l)
  def +(s: ComposedShape[MyType]): ComposedShape[MyType] = this.and(s)
  def map[OutType <: Shape](f: MyType => OutType): ComposedShape[OutType] = ComposedShape(this.l.map(f))
  def flatMap[OutType <: Shape](f: MyType => Iterable[OutType]) : ComposedShape[OutType] = ComposedShape(this.l.flatMap(f))
  def foreach[B](f: MyType => B): Unit = l.foreach(f)
  override def moveX(v: Double): Unit = this.foreach(s => s.moveX(v))
  override def moveY(v: Double): Unit = this.foreach(s => s.moveY(v))
  override def move(x: Double)(y: Double): Unit = this.foreach(s => s.move(x)(y))
  def moveXto(v: Double): Unit = this.foreach(s => s.moveXto(v))
  def moveYto(v: Double): Unit = this.foreach(s => s.moveYto(v))
  override def moveTo(x: Double)(y: Double): Unit = this.foreach(s => s.moveTo(x)(y))
  override def color(c: String): Unit = this.foreach(s => s.color(c))
  override def strokeWidth(sW: Double): Unit = this.foreach(s => s.strokeWidth(sW))
  override def strokeColor(sS: String): Unit = this.foreach(s => s.strokeColor(sS))
  override def fill(f: Boolean): Unit = this.foreach(s => s.fill(f))
  def shadowOffsetX(sO: Double): Unit = this.foreach(s => s.shadowOffsetX(sO))
  def shadowOffsetY(sO: Double): Unit = this.foreach(s => s.shadowOffsetY(sO))
  def shadowBlur(sB: Double): Unit = this.foreach(s => s.shadowBlur(sB))
  def shadowColor(sC: String): Unit = this.foreach(s => s.shadowColor(sC))
  override def attachImage(url: String): Unit = this.foreach(s => s.attachImage(url))
  override def change(property: CanvasElementModifier[A]): Unit = this.foreach(s => property.change(s))
}

/**
 * an Rectangle class representing a rectangle
 * @param x the position of the top right corner on the x axis
 * @param y the position of the top right corner on the y axis
 * @param width the width of the rectangle
 * @param height the height of the rectangle
 */
case class Rectangle(var x: Double, var y: Double, var width: Double, var height: Double) extends SingleShape with Ordered[Rectangle] {
  type A = Rectangle
  def and(s: Rectangle): ComposedShape[Rectangle] = ComposedShape(List(this, s))
  def +(s: Rectangle): ComposedShape[Rectangle] = this.and(s)
  def and(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Rectangle]): ComposedShape[Rectangle] = this.and(s)
  override def inside(x: Double)(y: Double): Boolean = x > this.x && x < this.x+this.width && y < this.y+this.height && y > this.y
  override def compare(that: Rectangle): Int = {
    if (this.x == that.x && this.y == that.y && this.width == that.width) {
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

/**
 * an Square class representing a square
 * @param x the position of the top right corner on the x axis
 * @param y the position of the top right corner on the y axis
 * @param len the length on each sides of the square
 */
case class Square(var x: Double, var y: Double, var len: Double) extends SingleShape with Ordered[Square] {
  type A = Square
  def and(s: Square): ComposedShape[Square] = ComposedShape(List(this, s))
  def +(s: Square): ComposedShape[Square] = this.and(s)
  def and(s: ComposedShape[Square]): ComposedShape[Square] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Square]): ComposedShape[Square] = this.and(s)
  override def inside(x: Double)(y: Double): Boolean = x > this.x && x < this.x+this.len && y < this.y+this.len && y > this.y
  override def compare(that: Square): Int = {
    if (this.x == that.x && this.y == that.y) {
      (this.len - that.len).asInstanceOf[Int]
    } else if (this.x == that.x) {
      (this.y - that.y).asInstanceOf[Int]
    } else {
      (this.x - that.x).asInstanceOf[Int]
    }
  }
}

/**
 * an Circle class representing an circle
 * @param x the position of the center of gravity on the x axis
 * @param y the position of the center of gravity on the y axis
 * @param radius the radius of the circle
 */
case class Circle(var x: Double, var y: Double, var radius: Double) extends SingleShape with Ordered[Circle] {
  type A = Circle
  def and(s: Circle): ComposedShape[Circle] = ComposedShape(List(this, s))
  def +(s: Circle): ComposedShape[Circle] = this.and(s)
  def and(s: ComposedShape[Circle]): ComposedShape[Circle] = ComposedShape(this::s.l)
  def +(s: ComposedShape[Circle]): ComposedShape[Circle] = this.and(s)
  override def inside(x: Double)(y: Double): Boolean = Math.sqrt(Math.pow(this.x - x, 2)+Math.pow(this.y - y, 2)) <= this.radius
  override def compare(that: Circle): Int = {
    if (this.x == that.x && this.y == that.y) {
      (this.radius - that.radius).asInstanceOf[Int]
    } else if (this.x == that.x) {
      (this.y - that.y).asInstanceOf[Int]
    } else {
      (this.x - that.x).asInstanceOf[Int]
    }
  }
}

/**
 * an Text class representing an text
 * @param x the position on the x axis
 * @param y the position on the y axis
 * @param txt the text that must be drawn
 */
case class Text(var x: Double, var y: Double, var txt: String) extends SingleShape {
  type A = Text
  var font: String = "sans-serif"
  var fontSize: Double = 10
  var textAlign: String = "start"
  var textBaseline: String = "alphabetic"
  filled = true
  color = "black"

  def font(font: String): Unit = this.font = font
  def fontSize(fontSize: Double): Unit = this.fontSize = fontSize
  def textAlign(textAlign: String): Unit = this.textAlign = textAlign
  def textBaseline(textBaseline: String): Unit = this.textBaseline = textBaseline
}

/**
 * an Grid class representing an grid
 * @param length the length of a single bax inside the grid
 * @param nb_row number of row the gris must contains
 * @param nb_col number of colon the gris must contains
 * @param strokeColor the color of the box strokes
 * @param wall boolean value indicate whether the grid contains wall or not
 * @param wallColor the color of the wall
 */
class Grid(length: Int, nb_row: Int, nb_col: Int, strokeColor: String, wall: Boolean, wallColor: String)
  extends ComposedShape[Rectangle](Grid.build_grid(length, nb_row, nb_col, wall, wallColor).toList) {
  l change StrokeColor(strokeColor)

  /**
   * fill a box inside the grid
   * @param unitX the position on the x axis
   * @param unitY the position on the y axis
   * @param color the color of the box filled
   */
  def fillGridCase(unitX: Int, unitY: Int, color: String): Unit = {
    l = for(rec <- l) yield
      if(rec == Rectangle(unitX*length, unitY*length, 1*length, 1*length)) {
        rec change Color(color)
        rec change Fill(true)
        rec
      } else {
        rec
      }
  }

  /**
   * unfill a box inside the grid
   * @param unitX the position on the x axis
   * @param unitY the position on the y axis
   */
  def unFillGridCase(unitX: Int, unitY: Int): Unit = {
    l = for(rec <- l) yield
      if(rec == Rectangle(unitX*length, unitY*length, 1*length, 1*length)){
        rec change Fill(false)
        rec
      } else {
        rec
      }
  }
}

object Grid {
  /**
   * this allows to creat an Canvasy object without the 'new' keyword
   * @param length the length of a single bax inside the grid
   * @param nb_row number of row the gris must contains
   * @param nb_col number of colon the gris must contains
   * @param strokeColor the color of the box strokes
   * @param wall boolean value indicate whether the grid contains wall or not
   * @param wallColor the color of the wall
   * @return a new Grid instance
   */
  def apply(length: Int, nb_row: Int, nb_col: Int, strokeColor: String, wall: Boolean, wallColor: String): Grid =
    new Grid(length: Int, nb_row, nb_col, strokeColor, wall, wallColor)

  /**
   * build a grid
   * @param length the length of a single bax inside the grid
   * @param nb_row number of row the gris must contains
   * @param nb_col number of colon the gris must contains
   * @param wall boolean value indicate whether the grid contains wall or not
   * @param wallColor the color of the wall
   * @return an IndexedSeq instance representing the grid
   */
  def build_grid(length: Int, nb_row: Int, nb_col: Int, wall: Boolean, wallColor: String): IndexedSeq[Rectangle] = {
    for(i <- 0 to nb_row; j <-  0 to nb_col) yield {
        val rec = Rectangle(j*length, i*length, 1*length, 1*length)
        if (wall && (i == 0 || i == nb_row-1 || j == 0 || j == nb_col-1)) {
          rec change Fill(true)
          rec change Color(wallColor)
        }
        rec
      }
  }
}