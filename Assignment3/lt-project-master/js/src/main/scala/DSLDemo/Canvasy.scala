package DSLDemo

import org.scalajs.dom
import org.scalajs.dom.html

class Canvasy(canvas: html.Canvas) {
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  var shapes: List[Shape] = List()
  def +=(s:Shape): Unit = shapes = shapes ++ List(s)
  def draw(): Unit = {
    shapes.foreach(x => draw(x))
  }
  def draw(shape:Shape): Unit = {
    ctx.beginPath()
    shape match {
      case Rectangle(x, y, width, height) => {
        ctx.strokeStyle = shape.asInstanceOf[Rectangle].color
        ctx.lineWidth = shape.asInstanceOf[Rectangle].strokeWidth
        ctx.rect(x, y, width, height)
      }
      case Circle(x, y, radius) => {
        ctx.strokeStyle = shape.asInstanceOf[Circle].color
        ctx.lineWidth = shape.asInstanceOf[Circle].strokeWidth
        ctx.arc(x, y, radius, 0, 2 * Math.PI);
      }
      case ComposedShape(sh) => sh.foreach(s => draw(s))
    }
    ctx.stroke()
  }
}
