package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Event}
import org.scalajs.dom.raw.HTMLImageElement


class Image(src: String) { // TODO permettre de reshape
  private var loaded: Boolean = false
  val image: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  image.src = src
  image.onload = {_: Event =>  loaded = true}
  def draw(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit = {
    if(this.loaded) ctx.drawImage(image, x, y)
    else image.onload = {_: Event =>  ctx.drawImage(image, x, y)}
  }
}

object Image{
  def apply(src: => String): Image = new Image(src)
}
