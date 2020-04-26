package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Event}
import org.scalajs.dom.raw.HTMLImageElement


class Image(src: String) {
  val image: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  image.src = src
}

object Image{
  def apply(src: => String): Image = new Image(src)
  def draw(img: Image, unitX: Int, unitY: Int, ctx: CanvasRenderingContext2D): Unit = {
    img.image.onload = {_: Event =>  ctx.drawImage(img.image, unitX, unitY)}
  }
}
