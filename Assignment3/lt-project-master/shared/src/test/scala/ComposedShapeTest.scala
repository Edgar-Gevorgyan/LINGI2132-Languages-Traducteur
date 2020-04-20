import DSLDemo._

import common.Common._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ComposedShapeTest extends AnyFunSuite with Matchers {

  Random.setSeed(947693)

  test("Moving a group of shapes up") {
    val l = newRectangleList()
    val g = ComposedShape(l)
    val offSet = Random.nextInt(20)
    val expected = l.map(_.y - offSet)
    g moveY(-offSet)
    for (i <- l.indices)
      g(i).asInstanceOf[Rectangle].y shouldBe expected(i)
  }

  test("foreach simple") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = ComposedShape(rl ++ cl)
    var counter = 0
    g.foreach(x => counter += 1)
    counter shouldBe rl.length + cl.length
  }

  test("map") {
    val rl = newRectangleList(10)
    val expected = List.tabulate(rl.length)(i => Circle(rl(i).x, rl(i).y, 20))
    val g = ComposedShape(rl)
    val newG = g.map(r => Circle(r.asInstanceOf[Rectangle].x, r.asInstanceOf[Rectangle].y, 20))
    for (i <- rl.indices) {
      g(i) shouldBe rl(i)
      newG(i) shouldBe expected(i)
      assert(g(i) != newG(i))
    }
  }

  // added
  test("implicit"){
    val g = newRectangleList()
    val offSet = Random.nextInt(20)
    val expected = g.map(_.y - offSet)
    g.moveY(-offSet)
    for (i <- g.indices)
      g(i).y shouldBe expected(i)
  }

  test("implicit2"){
    val g = newRectangleList()
    val expected = "blue"
    g change Color("blue")
    for (i <- g.indices)
      g(i).color shouldBe expected
  }

  test("implicit3") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = rl ++ cl
    var counter = 0
    g.foreach(x => counter += 1)
    counter shouldBe rl.length + cl.length
  }

  test("implicit4") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = rl and cl
    var counter = 0
    g.foreach(x => counter += 1)
    counter shouldBe 2
  }
  test("implicit5") {
    val circles: Array[Circle] = Array(Circle(50, 0, 0),Circle(50, 0, 0),Circle(50, 0, 0),Circle(50, 0, 0))
    val rectangles = Array.tabulate(5)(i => Rectangle(i*10, i*10, 10, 30))
    val cc = circles and rectangles
  }

  test("ewaa"){
    val c = Circle(2,2,2)
    val l:Array[Circle] = Array(Circle(0,0,0),Circle(1,1,1),c)
    l(2).radius shouldBe 2
    c.radius = 10
    l(2).radius shouldBe 10
  }

  test("should trow error"){
    val circles = Array.fill(4)(Circle(50, 0, 0))
    val rectangles = Array.tabulate(5)(i => Rectangle(i*10, i*10, 10, 30))
    val compo = rectangles(0) + circles(0)
    intercept[Exception] {
      compo change Width(30)
    }
    rectangles(0).width shouldBe 30
  }
}
