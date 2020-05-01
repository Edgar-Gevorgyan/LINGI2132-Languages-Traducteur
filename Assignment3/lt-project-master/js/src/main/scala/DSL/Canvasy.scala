package DSL

import DSL.Utils._
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas

class Canvasy(canvas: html.Canvas) {
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private var shapes: List[Shape] = List()
  private var grid: Grid = Grid(1,0,0,"black",wall = false,"red")// only for initialization
  private var imageCache: List[Image] = List()
  var unit: Int = 1

  /**
   * @param s the shape added to 'shapes' see draw() function for the utility of this function
   */
  def +=(s:Shape): Unit = {
    shapes = shapes ++ List(s)
    shapes.foreach {
      case s: SingleShape =>
        if(s.imageAttached){
          imageCache = Image(s.imageURL) :: imageCache
        }
      case _ =>
    }
  }

  /**
   * @param i the image i to be added to the 'imageCache' see draw(Shape) function for the utility of this function
   */
  def +=(i:Image): Unit = {
    imageCache = i :: imageCache
  }

  /**
   * @param unit the new unit of the canvas
   */
  def setUnit(unit: Int): Unit = this.unit = unit

  /**
   * draw all shapes inside the 'shapes' variable
   */
  def draw(): Unit = {
    shapes.foreach(x => draw(x))
  }

  /**
   *
   * @param shape the shape to be drawn
   *              if an image is attached to this shape and the image is inside the 'imageCache'
   *              then the execution is faster (because no need to load the image)
   */
  def draw(shape: Shape): Unit = {
    shape match {
      case ComposedShape(sh) => sh.foreach(s => draw(s))
      case s:  SingleShape =>
        ctx.beginPath()
        ctx.shadowOffsetX = s.shadowOffsetX
        ctx.shadowOffsetY = s.shadowOffsetY
        ctx.shadowBlur = s.shadowBlur
        ctx.shadowColor = s.shadowColor
        ctx.strokeStyle = s.strokeColor
        ctx.lineWidth = s.strokeWidth
        ctx.fillStyle = s.color
        s match {
          case Rectangle(x, y, width, height) =>
            ctx.rect(x*unit, y*unit, width*unit, height*unit)
            if(s.filled) ctx.fill()
            if(s.imageAttached){
              val found = imageCache.find(x => x.src eq s.imageURL)
              val img: Image = found.getOrElse(Image(s.imageURL))
              img.height = height*unit
              img.width = width*unit
              drawImage(img, x, y)
            }
          case Square(x, y, len) =>
            ctx.rect(x*unit, y*unit, len*unit, len*unit)
            if(s.filled) ctx.fill()
            if(s.imageAttached){
              val found = imageCache.find(x => x.src eq s.imageURL)
              val img: Image = found.getOrElse(Image(s.imageURL))
              img.height = len*unit
              img.width = len*unit
              drawImage(img, x, y)
            }
          case Circle(x, y, radius) =>
            ctx.arc(x*unit, y*unit, radius*unit, 0, 2 * Math.PI)
            if(s.filled) ctx.fill()
            if(s.imageAttached){
              val found = imageCache.find(x => x.src eq s.imageURL)
              val img: Image = found.getOrElse(Image(s.imageURL))
              img.height = 2*radius*unit
              img.width = 2*radius*unit
              drawImage(img, x - radius, y - radius)
            }
          case Text(x, y, txt) =>
            val t = s.asInstanceOf[Text]
            ctx.font = t.fontSize.toString + "px " + t.font
            ctx.textAlign = t.textAlign
            ctx.textBaseline = t.textAlign
            if(t.filled) ctx.fillText(txt, x*unit, y*unit)
            else ctx.strokeText(txt, x*unit, y*unit)
        }
        ctx.stroke()
    }
  }

  /**
   * @param img the image to be drawn at (unitX,unitY)
   * @param unitX the right top corner position on the x axis
   * @param unitY the right top corner position on the y axis
   */
  def drawImage(img: Image, unitX: Double, unitY: Double): Unit = img.draw(unitX*unit,unitY*unit,ctx)

  /**
   *  remove all shapes/image on the screen
   */
  def clear(): Unit = ctx.clearRect(0, 0, canvas.width, canvas.height)

  /**
   * build a grid can be used as a game ground
   * @param length the length of a single bax inside the grid
   * @param strokeColor the color of the box strokes
   * @param wall boolean value indicate whether the grid contains wall or not
   * @param wallColor the color of the wall
   * @return a Grid object
   */
  def buildGrid(length: Int = 1,strokeColor: String = "black", wall: Boolean = false, wallColor: String = "black"): Grid = {
    val nb_row = Math.floor(canvas.height / (unit*length)).asInstanceOf[Int]
    val nb_col = Math.floor(canvas.width / (unit*length)).asInstanceOf[Int]
    grid = Grid(length, nb_row, nb_col, strokeColor, wall, wallColor)
    grid
  }

  /**
   * fill a box inside the grid
   * @param unitX the position on the x axis
   * @param unitY the position on the y axis
   * @param color the color of the box filled
   */
  def fillGridCase(unitX: Int, unitY: Int, color: String = "black"): Unit = grid.fillGridCase(unitX,unitY,color)

  /**
   * unfill a box inside the grid
   * @param unitX the position on the x axis
   * @param unitY the position on the y axis
   */
  def unFillGridCase(unitX: Int, unitY: Int): Unit = grid.unFillGridCase(unitX, unitY)
}

object Canvasy {
  /**
   * this allow to creat an Canvasy object without the 'new' keyword
   * @return new Canvasy instance
   */
  def apply(canvas: Canvas): Canvasy = new Canvasy(canvas)
}