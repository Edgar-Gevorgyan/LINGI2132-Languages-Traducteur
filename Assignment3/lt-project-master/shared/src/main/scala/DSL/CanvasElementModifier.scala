package DSL

trait CanvasElementModifier[-ApplyOn <: Shape] {
  def change(x: ApplyOn): Unit
}

case class Color(c: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.color(c)
}

case class StrokeColor(sS: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.strokeColor(sS)
}

case class StrokeWidth(sW: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.strokeWidth(sW)
}

case class Fill(f: Boolean) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.fill(f)
}

case class AttachImage(url: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.attachImage(url)
}

case class Width(w: Double) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.width = w
}

case class Height(h: Double) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.height = h
}

case class Radius(r: Double) extends CanvasElementModifier[Circle] {
  override def change(x: Circle): Unit = x.radius = r
}

case class Font(font: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.font = font
}

case class FontSize(fontSize: Double) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.fontSize = fontSize
}

case class TextAlign(textAlign: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.textAlign = textAlign
}

case class TextBaseline(textBaseline: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.textBaseline = textBaseline
}