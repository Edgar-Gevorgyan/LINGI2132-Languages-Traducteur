package webapp

import DSL.Utils._
import DSL._
import org.scalajs.dom
import org.scalajs.dom.{document, html}


object Main {


  def main(args: Array[String]): Unit = {

    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    val w = 608
    canvas.width = w
    canvas.height = w
    var canvasy = Canvasy(canvas)
    // --------- example usage Timer ---------
    //atEach(1000) execute appendPar(document.body,"ee")
    //atEach(500) execute appendPar(document.body,"ewa")
    //after(5000) execute Timer.remove()
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
    //canvasy.drawImg(i,0,0) // WORK
    //after(300) execute canvasy.drawImg(i,0,0)
    // --------- example usage Grid ---------
    //canvasy.setUnit(32)
    //val v = canvasy.buildGrid()
    //v change StrokeColor("blue")
    //v fillGridCase (2,2,"red")
    //canvasy.draw(v)
    // --------- example usage MouseListener ---------
    //canvasy.setUnit(32)
    //canvasy.draw(canvasy.buildGrid())
    //val rec = Rectangle(2,2,1,1)
    //MouseListener.onChangeInside(rec, canvasy.unit){ appendPar(document.body, "ewaaa" )}
    //****************SNAKE****************

    //val ground = Image("IMG/ground.png")
    //canvasy.drawImage(ground,0,0)



    snakeGame(canvas)

  }

  def snakeGame(canvas: html.Canvas): Unit = {
    val canvasy = Canvasy(canvas)
    val box = 32
    canvasy.setUnit(box)

    val ground = Image("IMG/ground.png")
    // Cela marche aussi
    // val ground = Image("https://images-na.ssl-images-amazon.com/images/I/71N1XtP8TdL._AC_SX466_.jpg")

    var snake = Array[Rectangle](Rectangle(9,10,1,1)) // TODO faire un Square qui herite de Rectangle

    var foodX = Math.floor(Math.random() * 17 + 1).asInstanceOf[Int]
    var foodY = Math.floor(Math.random() * 15 + 3).asInstanceOf[Int]

    val eat = Audio("AUDIO/eat.mp3")

    var score = 0
    var d = "init"
    KeyListener.onChange(Key.LEFT) {if (d != "RIGHT") d = "LEFT"}
    KeyListener.onChange(Key.RIGHT) {if (d != "LEFT") d = "RIGHT"}
    KeyListener.onChange(Key.UP) {if (d != "DOWN") d = "UP"}
    KeyListener.onChange(Key.DOWN) {if (d != "UP") d = "DOWN"}
    KeyListener.onChange(Key.SPACE) {appendPar(document.body,d)}

    val start_layout = Rectangle(5,9,9,2)
    canvasy += start_layout
    start_layout change Fill(true)
    start_layout change Color("skyblue")

    val start_text = Text(6.5,10.4,"click to start")
    start_text change FontSize(34)
    canvasy += start_text

    canvasy.draw()

    canvasy += Image("IMG/food.png") // IMPROVE PERFORMANCE

    MouseListener.onChangeInside(start_layout,box){
      MouseListener.clear()
      atEach(100) execute {
        canvasy.clear()

        canvasy.drawImage(ground,0,0)
        snake change Fill(true)
        canvasy.draw(snake)


        val food = Circle(foodX + 0.5, foodY + 0.5, 0.5) // attention 1/2 retourne 0 (division entiere)
        food change Color("blue")
        food change Fill(true)
        food change ShadowColor("Red")
        food change ShadowBlur(10)
        food change AttachImage("IMG/food.png")
        canvasy.draw(food)


        var snakeX = snake(0).x
        var snakeY = snake(0).y


        if (d eq "LEFT") snakeX -= 1
        if (d eq "UP") snakeY -= 1
        if (d eq "RIGHT") snakeX += 1
        if (d eq "DOWN") snakeY += 1

        if (snakeX == foodX && snakeY == foodY) {
          score += 1
          eat.play()
          foodX = Math.floor(Math.random() * 17 + 1).asInstanceOf[Int]
          foodY = Math.floor(Math.random() * 15 + 3).asInstanceOf[Int]
        } else {
          snake = snake.take(snake.length - 1)
        }

        val newHead = Rectangle(snakeX,snakeY,1,1)
        newHead change AttachImage("IMG/food.png")

        if (snakeX < 1 || snakeX > 17  || snakeY < 3 || snakeY > 17 || snake.contains(newHead)) {
          Timer.remove()
          val finish_text = Text(6.3,10.5,"GAME OVER")
          finish_text change Color("red")
          finish_text change FontSize(34)
          canvasy.draw(start_layout)
          canvasy.draw(finish_text)

          MouseListener.onChangeInside(start_layout,canvasy.unit){
            snakeGame(canvas)
          }
        }

        snake +:= newHead

        val t = Text(2 , 1.6, score.toString)
        t change Fill(true)
        t.fontSize = 30
        canvasy.draw(t)
      }
    }
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
    ctx.moveTo(w * 2/3, 0)
    ctx.lineTo(w * 2/3, w/3)
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
    rectangles(0) + rectangles change Width(30)



    // You can have a nice draw function to draw all of this on the canvas
    canvasy.draw()
  }

}
