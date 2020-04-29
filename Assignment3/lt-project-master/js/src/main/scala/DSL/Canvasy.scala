package DSL

import DSL.Utils._
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas

class Canvasy(canvas: html.Canvas) { // TODO quadratic and bzier curve :: https://developer.mozilla.org/fr/docs/Tutoriel_canvas/Formes_géométriques
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private var shapes: List[Shape] = List()
  private var unit: Int = 1
  private var wall: Boolean = false
  private var grid: Grid = Grid(0,0,"black",wall = false,"red")// only for initialization

  def +=(s:Shape): Unit = shapes = shapes ++ List(s)

  def draw(): Unit = {
    shapes.foreach(x => draw(x))
  }

  def draw(shape: Shape): Unit = {
    shape match {
      case ComposedShape(sh) => sh.foreach(s => draw(s))
      case _ =>
        ctx.beginPath()
        val s = shape.asInstanceOf[SingleShape]
        ctx.strokeStyle = s.strokeColor
        ctx.lineWidth = s.strokeWidth
        ctx.fillStyle = s.color
        s match {
          case Rectangle(x, y, width, height) =>
            ctx.rect(x*unit, y*unit, width*unit, height*unit)
            if(s.filled) ctx.fill()
          case Circle(x, y, radius) =>
            ctx.arc(x*unit, y*unit, radius*unit, 0, 2 * Math.PI)
            if(s.filled) ctx.fill()
        }
        ctx.stroke()
    }
  }

  def drawImage(img: Image, unitX: Double, unitY: Double): Unit = img.draw(unitX*unit,unitY*unit,ctx)


  def drawText(txt: Text, unitX: Double, unitY: Double): Unit = txt.drawText(unitX*unit,unitY*unit,ctx)

  def clear(): Unit = ctx.clearRect(0, 0, canvas.width, canvas.height)

  def setUnit(unit: Int): Unit = this.unit = unit


  def makeGrid(color: String = "black", wall: Boolean = false, wallColor: String = "black"): Unit = {
    this.wall = wall
    val nb_row = Math.floor(canvas.height / unit).asInstanceOf[Int]
    val nb_col = Math.floor(canvas.width / unit).asInstanceOf[Int]
    grid = Grid(nb_row, nb_col, color, wall, wallColor)
  }
  def fillGridCase(unitX: Int, unitY: Int, color: String = "black"): Unit = grid.fillGridCase(unitX,unitY,color)
  def unFillGridCase(unitX: Int, unitY: Int): Unit = grid.unFillGridCase(unitX, unitY)

  def drawGrid(): Unit = draw(grid.grid)
}

object Canvasy {
  def apply(canvas: Canvas): Canvasy = new Canvasy(canvas)
}

