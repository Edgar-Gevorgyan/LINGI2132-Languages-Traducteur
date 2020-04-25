package DSL

import org.scalajs.dom
import org.scalajs.dom.html

class Canvasy(canvas: html.Canvas) {
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private var shapes: List[Shape] = List()
  private var wall: Boolean = false

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
        ctx.arc(x, y, radius, 0, 2 * Math.PI);
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
    val nb_row = Math.floor(canvas.height / unit).asInstanceOf[Int]
    val nb_col = Math.floor(canvas.width / unit).asInstanceOf[Int]
    var grid:ComposedShape[Rectangle] = ComposedShape(Nil)
    for(i <- 0 until nb_row){
      for(j <-  0 until nb_col){
        val rec = Rectangle(unit*j,unit*i,unit,unit)
        rec change Color(color)
        if(wall && (i == 0 || i == nb_row-1 || j == 0 || j == nb_col-1)) {
          rec change Fill(true)
          rec change Color(wallColor)
        }
        grid = rec + grid
      }
    }
    draw(grid)
  }
}

