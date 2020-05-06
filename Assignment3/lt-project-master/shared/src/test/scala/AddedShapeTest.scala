import DSL._
import common.Common._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class AddedShapeTest extends AnyFunSuite with Matchers{

  Random.setSeed(947693)

  test("implicit conversion test"){
    val g = newRectangleList()
    val offSet = Random.nextInt(20)
    val expected = g.map(_.y - offSet)
    g.moveY(-offSet)
    for (i <- g.indices)
      g(i).y shouldBe expected(i)
  }

  test("implicit conversion test 2") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = rl ++ cl
    var counter = 0
    g.foreach(x => counter += 1)
    counter shouldBe rl.length + cl.length
  }

  test("implicit conversion test 3") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = rl and cl
    var counter = 0
    g.foreach(_ => counter += 1)
    counter shouldBe 2
  }

  test("map test"){
    val rl = newRectangleList()
    val g = ComposedShape(rl)
    val cs = g.map(s => Circle(s.x,s.y,1))
    cs change Radius(10)
    for (i <- cs)
      i.radius shouldBe 10
  }


  test("Move rectangle") {
    val destY = Random.nextInt(20)
    val rectangle = newRectangle()
    rectangle moveYto destY
    rectangle.y shouldBe destY
  }

  test("Move x and y rectangle") {
    val destY = Random.nextInt(20)
    val destX = Random.nextInt(20)
    val rectangle = newRectangle()
    rectangle.moveTo(destX)(destY)
    rectangle.x shouldBe destX
    rectangle.y shouldBe destY
  }

  test("Move offset x and y rectangle") {
    val destX = Random.nextInt(20)
    val destY = Random.nextInt(20)
    val rectangle = newRectangle()
    val expectedX = rectangle.x + destX
    val expectedY = rectangle.y + destY
    rectangle.move(destX)(destY)
    rectangle.x shouldBe expectedX
    rectangle.y shouldBe expectedY
  }

  test("Move offset x and y Text") {
    val destX = Random.nextInt(20)
    val destY = Random.nextInt(20)
    val txt = Text(0,0,"hello")
    val expectedX = txt.x + destX
    val expectedY = txt.y + destY
    txt.move(destX)(destY)
    txt.x shouldBe expectedX
    txt.y shouldBe expectedY
  }

  test("Move offset x and y Grid") {
    val destX = Random.nextInt(20)
    val destY = Random.nextInt(20)
    val g = Grid(2,2,2,"black",wall = false,"black")
    val expectedX = g(0).x + destX
    val expectedY = g(0).y + destY
    g.move(destX)(destY)
    g(0).x shouldBe expectedX
    g(0).y shouldBe expectedY
  }

  test("grid test"){
    val g = Grid(2,2,2,"black",wall = false,"black")
    g fillGridCase(0,0,"black")
    g(0).filled shouldBe true
    g(1).filled shouldBe false
    g unFillGridCase(0,0)
    g(0).filled shouldBe false
  }

  test("isInside Rectangle"){
    val rec = Rectangle(0,0,10,10)
    rec.isInside(5)(5) shouldBe true
  }

  test("isInside Circle"){
    val rec = Circle(0,0,10)
    rec.isInside(2)(2) shouldBe true
  }

  test("Attached Image on Square"){
    val sq = Square(0,0,1)
    sq change AttachImage("hello")
    sq.imageAttached shouldBe true
    sq.imageURL shouldBe "hello"
  }

  test("Not Attached Image on Text"){
    val tt = Text(0,0,"hi")
    tt.imageAttached shouldBe false
  }

  test("Change Text content") {
    val text = Text(0,0,"hello")
    val changed = "hi"
    text change Content(changed)
    text.txt shouldBe changed
  }

  test("Change ComposedShape of Text content") {
    val text1 = Text(0,0,"hello")
    val text2 = Text(0,0,"hello")
    val text3 = Text(0,0,"hello")
    val text4 = Text(0,0,"hello")
    val texts = List(text1,text2,text3,text4)
    val changed = "hi"
    texts change Content(changed)
    texts.foreach( t=>
      t.txt shouldBe changed
    )
  }

  test("Font in Text"){
    val tt = Text(0,0,"hi")
    tt change Font("hello")
    tt.font shouldBe "hello"
  }

  test("filled test") {
    val r = Square(1,1,1)
    r.filled shouldBe false
    r change Fill(false)
    r.filled shouldBe false
    r change Fill(false)
    r.filled shouldBe false
  }

  test("difference and equality for Rectangle") {
    val r = Rectangle(1,1,1,1)
    val r1 = Rectangle(1,1,1,1)
    val r2 = Rectangle(1,1,1,2)
    val r3 = Rectangle(1,1,2,1)
    val r4 = Rectangle(1,2,1,1)
    val r5 = Rectangle(2,1,1,1)
    r == r1 shouldBe true
    r != r2 shouldBe true
    r != r3 shouldBe true
    r != r4 shouldBe true
    r != r5 shouldBe true
    r < r2 shouldBe true
    r < r3 shouldBe true
    r < r4 shouldBe true
    r < r5 shouldBe true
    r2 > r shouldBe true
    r3 > r2 shouldBe true
  }
  test("difference and equality for Square") {
    val r = Square(1,1,1)
    val r1 = Square(1,1,1)
    val r2 = Square(1,1,2)
    val r3 = Square(1,2,1)
    val r4 = Square(2,1,1)
    r == r1 shouldBe true
    r != r2 shouldBe true
    r != r3 shouldBe true
    r != r4 shouldBe true
    r < r2 shouldBe true
    r < r3 shouldBe true
    r < r4 shouldBe true
    r2 > r shouldBe true
    r3 > r2 shouldBe true
  }

  test("difference and equality for Circle") {
    val c = Circle(1,1,1)
    val c1 = Circle(1,1,1)
    val c2 = Circle(1,1,2)
    val c3 = Circle(1,2,1)
    val c4 = Circle(2,1,1)
    c == c1 shouldBe true
    c != c2 shouldBe true
    c != c3 shouldBe true
    c != c4 shouldBe true
    c < c2 shouldBe true
    c < c3 shouldBe true
    c < c4 shouldBe true
    c2 > c shouldBe true
  }

}
