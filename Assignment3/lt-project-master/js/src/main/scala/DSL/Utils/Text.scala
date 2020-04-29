package DSL.Utils

import org.scalajs.dom.CanvasRenderingContext2D

class Text (txt: String){
  var fill: Boolean = true
  var font: String = "sans-serif"
  var fontSize: Double = 10
  var textAlign: String = "start"
  var textBaseline: String = "alphabetic"

  def fill(fill: Boolean): Unit = this.fill = fill

  def font(font: String): Unit = this.font = font

  def fontSize(fontSize: Double): Unit = this.fontSize = fontSize

  def textAlign(textAlign: String): Unit = this.textAlign = textAlign

  def textBaseline(textBaseline: String): Unit = this.textBaseline = textBaseline

  def drawText(x: Double, y: Double, ctx: CanvasRenderingContext2D): Unit ={
    ctx.font = this.fontSize.toString + "px " + this.font
    ctx.textAlign = this.textAlign
    ctx.textBaseline = this.textAlign
    if(this.fill) ctx.fillText(txt, x, y)
    else ctx.strokeText(txt, x, y)
  }
}

object Text{
  def apply(txt: String): Text = new Text(txt)
}
