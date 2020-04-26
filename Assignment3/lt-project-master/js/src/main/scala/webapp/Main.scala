package webapp

import DSL._
import org.scalajs.dom
import org.scalajs.dom.{document, html}


object Main {


  def main(args: Array[String]): Unit = {

    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    document.body.appendChild(canvas)

    val w = 608
    canvas.width = w
    canvas.height = w
    var canvasy = Canvasy(canvas)
    // --------- example usage Timer ---------
    //val id = atEach(1000) execute appendPar(document.body,"ee")
    //after(3001) execute Timer.remove(id)
    // --------- example usage KeyListener ---------
    //KeyListener.onChange(Key.UP){scalaJSDemo(canvas)}
    //val i = KeyListener.onChange(Key.DOWN){appendPar(document.body,"ee")}
    //KeyListener.onChange(Key.RIGHT){KeyListener.remove(i)}
    //after(3000) execute KeyListener.clear()
    // --------- example usage Grid ---------
    //canvasy.makeGrid(50)
    //canvasy.addObstacle(0,0,"red")
    //canvasy.addObstacle(5,4,"red")
    //anvasy.removeObstacle(5,4)
    //canvasy.drawGrid()
    // --------- example usage Image ---------
    //val i = Image("IMG/ground.png")
    //canvasy.drawImg(i,0,0)
  }


  def appendPar(targetNode: dom.Node, text: String): Unit = {// just to test on browser
    val parNode = document.createElement("p")
    parNode.textContent = text
    targetNode.appendChild(parNode)
  }

  def scalaJSDemo(c: html.Canvas): Unit = {
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val w = 300
    c.width = w
    c.height = w

    ctx.strokeStyle = "red"
    ctx.lineWidth = 3
    ctx.beginPath()
    ctx.moveTo(w/3, 0)
    ctx.lineTo(w/3, w/3)
    ctx.moveTo(w*2/3, 0)
    ctx.lineTo(w*2/3, w/3)
    ctx.moveTo(w, w/2)
    ctx.arc(w/2, w/2, w/2, 0, 3.14)

    ctx.stroke()
  }

  /*
   * TODO: When you've done the first part, you should be able to uncomment this
   *       method and call it without problems :-)
   */
  def useMySuperDSL(canvas: html.Canvas): Unit = {
    // After you've done the first part of the project, everything should
    // compile and do the expected behaviour
    val canvasy = new Canvasy(canvas)

    val circles = Array.fill(4)(Circle(50, 0, 10))// radius == 0 thus it's normal no circle at output
    val rectangles = Array.tabulate(5)(i => Rectangle(i*10, i*10, 10, 30))

    canvasy += circles
    canvasy += rectangles

    // First we can modify property of Shapes by modifying their property directly
    circles(0) color "red"
    rectangles(0) strokeWidth 10
    rectangles(1) moveX 10

    // We should also be able to do the same on a group of shapes
    // (list, array, iterables, ...)
    circles moveX 20

    // We can also change property using the CanvasElementModifier trait
    circles change Color("blue")

    // We can group the shapes easily with the keyword and
    val superGroupOfShapes = circles and rectangles

    // And of course, we have foreach/map/flatmap available
    (rectangles(0) and circles(1)).foreach(_ moveY 30)

    // We should also be able to use common operators to group shapes
    val anotherSuperGroup = rectangles ++ circles

    // We can get back the elements by their index
    val s = anotherSuperGroup(0)

    // Take care that some property change should not compile, like this one
    // (rectangles(0) + circles(0)) change Width(30)
    // because Circles have no width
    //(rectangles(0) + rectangles(1)) change Width(30)
    //circles(0) change Width(30)
    rectangles(0) + rectangles change Width(30) // TODO : must work



    // You can have a nice draw function to draw all of this on the canvas
    canvasy.draw()
  }

}
