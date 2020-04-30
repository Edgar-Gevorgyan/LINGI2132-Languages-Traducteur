package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Event}
import org.scalajs.dom.raw.HTMLImageElement


class Image(src: String) { // TODO permettre de reshape
  private var loaded: Boolean = false
  private val image: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]

  var width: Double = 0
  var height: Double = 0

  image.src = src
  image.onload = {_: Event =>
    width = image.naturalWidth
    height = image.naturalHeight
    loaded = true
  }


  def draw(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit = {
    if (this.loaded) safeDraw(x,y,ctx)
    else image.onload = {_: Event =>
      width = image.naturalWidth
      height = image.naturalHeight
      safeDraw(x,y,ctx)
    }
  }

  def safeDraw(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit = {
    ctx.drawImage(image, x, y,width,height)
  }
}

object Image{
  def apply(src: => String): Image = new Image(src)
}
