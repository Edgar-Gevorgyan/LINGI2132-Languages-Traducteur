import DSL._

import common.Common._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ElementaryShapeTest extends AnyFunSuite with Matchers {

  Random.setSeed(183945)

  test("Move rectangle up") {
    val offSet = Random.nextInt(20)
    val rectangle = newRectangle()
    val expected = rectangle.y - offSet
    rectangle moveY(-offSet)
    rectangle.y shouldBe expected
  }

  test("Move circle left") {
    val offSet = Random.nextInt(20)
    val circle = newCircle()
    val expected = circle.x - offSet
    circle moveX(-offSet)
    circle.x shouldBe expected
  }

  test("Change color of rectangle via keyword") {
    val rectangle = newRectangle()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    rectangle color newColor
    rectangle.color shouldBe newColor
  }

  test("Change color of circle via property") {
    val circle = newCircle()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    circle change Color(newColor)
    circle.color shouldBe newColor
  }

  test("filled test") {
    val r = Rectangle(1,1,1,1)
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
    assert(r == r1)
    assert(r != r2)
    assert(r != r3)
    assert(r != r4)
    assert(r != r5)
  }
  test("difference and equality for Circle") {
    val c = Circle(1,1,1)
    val c1 = Circle(1,1,1)
    val c2 = Circle(1,1,2)
    val c3 = Circle(1,2,1)
    val c4 = Circle(2,1,1)
    assert(c == c1)
    assert(c != c2)
    assert(c != c3)
    assert(c != c4)
  }

}
