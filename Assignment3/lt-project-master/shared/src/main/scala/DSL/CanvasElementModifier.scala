package DSL

/**
 * @tparam ApplyOn is the type on which the 'change' function can be executed
 */
trait CanvasElementModifier[-ApplyOn <: Shape] {
  def change(x: ApplyOn): Unit
}

/**
 * @param c the new color of the Shape instance
 *          remark : it is the color inside the shape thus to be noticeable on the screen the shape must be filled
 */
case class Color(c: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.color(c)
}

/**
 * @param sC the new color of the stroke around the Shape instance
 */
case class StrokeColor(sC: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.strokeColor(sC)
}

/**
 * @param sW the new stroke width around the Shape instance
 */
case class StrokeWidth(sW: Double) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.strokeWidth(sW)
}

/**
 * @param f the new boolean value indicate whether the Shape instance is filled
 */
case class Fill(f: Boolean) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.fill(f)
}

/**
 * @param sO the new offset on the x axis of the shadow around the Shape instance
 */
case class ShadowOffsetX(sO: Double) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.shadowOffsetX(sO)
}

/**
 * @param sO the new offset on the y axis of the shadow around the Shape instance
 */
case class ShadowOffsetY(sO: Double) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.shadowOffsetY(sO)
}

/**
 * @param sB the new blurring effect of the shadow around the Shape instance
 */
case class ShadowBlur(sB: Double) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.shadowBlur(sB)
}

/**
 * @param sC the new shadow color around the Shape instance
 */
case class ShadowColor(sC: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.shadowColor(sC)
}

/**
 * @param url the new url or the path inside the computer tu attach an image on the Shape instance
 */
case class AttachImage(url: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.attachImage(url)
}

/**
 * @param w the new width of the Rectangle instance
 */
case class Width(w: Double) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.width = w
}

/**
 * @param h the new height of the Rectangle instance
 */
case class Height(h: Double) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.height = h
}

/**
 * @param l the new length of the Square instance
 */
case class Length(l: Double) extends CanvasElementModifier[Square] {
  override def change(x: Square): Unit = x.len = l
}

/**
 * @param r the new radius of the Circle instance
 */
case class Radius(r: Double) extends CanvasElementModifier[Circle] {
  override def change(x: Circle): Unit = x.radius = r
}

/**
 * @param font the new font style of the Text instance
 */
case class Font(font: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.font = font
}

/**
 * @param fontSize the new font size of the Text instance
 */
case class FontSize(fontSize: Double) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.fontSize = fontSize
}

/**
 * @param textAlign the new text alignment of the Text instance
 *                  only {start (default), end, left, right, center} are valid values
 */
case class TextAlign(textAlign: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.textAlign = textAlign
}

/**
 * @param textBaseline the new baseline alignment of the Text instance
 *                     only {top, hanging, middle, alphabetic (default), ideographic, bottom} are valid values
 */
case class TextBaseline(textBaseline: String) extends CanvasElementModifier[Text] {
  override def change(x: Text): Unit = x.textBaseline = textBaseline
}