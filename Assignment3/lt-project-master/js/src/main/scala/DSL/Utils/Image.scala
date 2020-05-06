package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Event}
import org.scalajs.dom.raw.HTMLImageElement


/**
 * @param src the source where the image is stored, this can be a path in the computer or a web link
 *            example: Image("IMG/food.png") the food.png is stored in the IMG folder of this project
 *                      Image("https://images-na.ssl-images-amazon.com/images/I/71N1XtP8TdL._AC_SX466_.jpg")
 */
class Image(val src: String){
  private var loaded: Boolean = false
  private val image: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]

  var width: Double = -1
  var height: Double = -1

  image.src = src
  image.onload = {_: Event =>
    if (width == -1) width = image.naturalWidth
    if (height == -1) height = image.naturalHeight
    loaded = true
  }


  /**
   * @param x the position on x axis
   * @param y the position on y axis
   * @param ctx the context inside which the function draw
   */
  def draw(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit = {
    if (this.loaded) safeDraw(x, y, ctx)
    else image.onload = {_: Event =>
      if (width == -1) width = image.naturalWidth
      if (height == -1) height = image.naturalHeight
      safeDraw(x, y, ctx)
    }
  }

  /**
   * this function is a helper for the previous one
   * @param x the position on x axis
   * @param y the position on y axis
   * @param ctx the context inside which the function draw
   */
  private def safeDraw(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit = {
    ctx.drawImage(image, x, y, width, height)
  }
}

object Image{
  /**
   * @param src the source where the image is stored, this can be a path in the computer or a web link
   * @return this allow to creat an Image object without the 'new' keyword
   */
  def apply(src: => String): Image = new Image(src)
}