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

case class Width(w: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x match {
    case Rectangle(_,_, _, _) => x.asInstanceOf[Rectangle].width = w
    case _ => throw new IllegalArgumentException("no width attribute")
  }
}

case class Height(h: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x match {
    case Rectangle(_,_,_,_) => x.asInstanceOf[Rectangle].height = h
    case _ => throw new IllegalArgumentException("no height attribute")
  }
}

case class Radius(r: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = x match {
    case Circle(_,_,_) => x.asInstanceOf[Circle].radius = r
    case _ => throw new IllegalArgumentException("no radius attribute")
  }
}