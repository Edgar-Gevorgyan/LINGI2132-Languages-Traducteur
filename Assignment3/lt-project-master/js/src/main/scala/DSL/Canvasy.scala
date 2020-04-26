package DSL

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas

class Canvasy(canvas: html.Canvas) {
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private var shapes: List[Shape] = List()
  private var wall: Boolean = false
  private var unit: Int = 10
  private var grid: Grid = Grid(0,0,0,"black",wall = false,"red")// only for initialization

  def +=(s:Shape): Unit = shapes = shapes ++ List(s)


  def draw(): Unit = {
    shapes.foreach(x => draw(x))
  }
  def draw(shape:Shape):Unit = {
    ctx.beginPath()
    shape match {
      case Rectangle(x,y,width,height) =>
        ctx.strokeStyle = shape.asInstanceOf[Rectangle].color
        ctx.lineWidth = shape.asInstanceOf[Rectangle].strokeWidth
        ctx.rect(x, y, width, height)
        if(shape.asInstanceOf[Rectangle].filled){
          ctx.fillStyle = ctx.strokeStyle
          ctx.fill()
        }
      case Circle(x,y,radius) =>
        ctx.strokeStyle = shape.asInstanceOf[Circle].color
        ctx.lineWidth = shape.asInstanceOf[Circle].strokeWidth
        ctx.arc(x, y, radius, 0, 2 * Math.PI)
        if(shape.asInstanceOf[Circle].filled){
          ctx.fillStyle = ctx.strokeStyle
          ctx.fill()
        }
      case ComposedShape(sh) => sh.foreach(s => draw(s))
    }
    ctx.stroke()
  }

  def clear(): Unit = {
    ctx.clearRect(0, 0, canvas.width, canvas.height)
  }

  def makeGrid(unit: Int, color: String = "black", wall: Boolean = false, wallColor: String = "black"): Unit ={
    this.wall = wall
    this.unit = unit
    val nb_row = Math.floor(canvas.height / unit).asInstanceOf[Int]
    val nb_col = Math.floor(canvas.width / unit).asInstanceOf[Int]
    grid = Grid(unit, nb_row, nb_col, color, wall, wallColor)
  }
  def addObstacle(unitX: Int, unitY: Int, color: String = "black"): Unit = grid.addObstacle(unitX,unitY,color)
  def removeObstacle(unitX: Int, unitY: Int): Unit = grid.removeObstacle(unitX, unitY)
  def drawGrid(): Unit = draw(grid.grid)
}

object Canvasy{
  def apply(canvas: Canvas): Canvasy = new Canvasy(canvas)
}

