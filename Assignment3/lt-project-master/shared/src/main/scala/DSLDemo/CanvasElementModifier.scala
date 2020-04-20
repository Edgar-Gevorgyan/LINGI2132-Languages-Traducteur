package DSLDemo

trait CanvasElementModifier[ApplyOn <: Shape] {
  def change(x: ApplyOn): Unit
}

case class Color(c: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.color(c)
}

case class StrokeWidth(sW: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x.strokeWidth(sW)
}

case class Width(w: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.width = w
}

case class Height(h: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.height = h
}

case class Radius(r: Int) extends CanvasElementModifier[Circle] {
  override def change(x: Circle): Unit = x.radius = r
}