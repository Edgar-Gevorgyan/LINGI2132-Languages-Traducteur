package DSL

import DSL.Utils._
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas

class Canvasy(canvas: html.Canvas) {
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private var shapes: List[Shape] = List()
  private var grid: Grid = Grid(1,0,0,"black",wall = false,"red")// only for initialization
  var imageCache: List[Image] = List()
  var unit: Int = 1

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
  def +=(i:Image): Unit = {
    imageCache = i :: imageCache
  }

  def draw(): Unit = {
    shapes.foreach(x => draw(x))
  }

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
              found match {
                case Some(value) =>
                  val img = value.asInstanceOf[Image]
                  img.height = height*unit
                  img.width = width*unit
                  drawImage(img, x,y)
                case None =>
                  val img = Image(s.imageURL)
                  img.height = height*unit
                  img.width = width*unit
                  drawImage(img, x, y)
              }


            }
          case Circle(x, y, radius) =>
            ctx.arc(x*unit, y*unit, radius*unit, 0, 2 * Math.PI)
            if(s.filled) ctx.fill()
            if(s.imageAttached){
              val found = imageCache.find(x => x.src eq s.imageURL)
              found match {
                case Some(value) =>
                  val img = value.asInstanceOf[Image]
                  img.height = 2*radius*unit
                  img.width = 2*radius*unit
                  drawImage(img, x,y)
                case None =>
                  val img = Image(s.imageURL)
                  img.height = 2*radius*unit
                  img.width = 2*radius*unit
                  drawImage(img, x - radius, y - radius)
              }
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

  def drawImage(img: Image, unitX: Double, unitY: Double): Unit = img.draw(unitX*unit,unitY*unit,ctx)



  def clear(): Unit = ctx.clearRect(0, 0, canvas.width, canvas.height)

  def setUnit(unit: Int): Unit = this.unit = unit


  def buildGrid(length: Int = 1,color: String = "black", wall: Boolean = false, wallColor: String = "black"): Grid = {
    val nb_row = Math.floor(canvas.height / (unit*length)).asInstanceOf[Int]
    val nb_col = Math.floor(canvas.width / (unit*length)).asInstanceOf[Int]
    grid = Grid(length, nb_row, nb_col, color, wall, wallColor)
    grid
  }
  def fillGridCase(unitX: Int, unitY: Int, color: String = "black"): Unit = grid.fillGridCase(unitX,unitY,color)
  def unFillGridCase(unitX: Int, unitY: Int): Unit = grid.unFillGridCase(unitX, unitY)
}

object Canvasy {
  def apply(canvas: Canvas): Canvasy = new Canvasy(canvas)
}

