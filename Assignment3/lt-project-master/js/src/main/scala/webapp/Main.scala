package webapp

import DSL.Utility._
import DSL._
import org.scalajs.dom
import org.scalajs.dom.{document, html}

import scala.util.Random


object Main {


  def main(args: Array[String]): Unit = {

    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    val w = 608
    canvas.width = w
    canvas.height = w

    snakeGame(canvas)
    //pongGame(canvas)
  }

  def pongGame(canvas: html.Canvas): Unit = {
    val rand = new Random(7)

    val canvasy = Canvasy(canvas)
    val box = 32
    canvasy.setUnit(box)

    // background
    var bord = Rectangle(0, 0, 19, 19)
    bord change Color("lightgray")
    bord change Fill(true)
    canvasy += bord

    // player1
    var player1 = Rectangle(0.1, 7.5, 1, 3)
    player1 change StrokeColor("red")
    player1 change Color("red")
    player1 change Fill(true)
    canvasy += player1

    // player1 listeners
    KeyListener.onChange(Key.Q)  { player1 moveY 1}
    KeyListener.onChange(Key.D) { player1 moveY -1}

    // player1 scores
    var score1 = 0
    var score1_text = Text(4.5, 3, score1.toString)
    score1_text change FontSize(70)
    canvasy += score1_text

    // player2
    var player2 = Rectangle(17.8, 7.5, 1, 3)
    player2 change StrokeColor("blue")
    player2 change Color("blue")
    player2 change Fill(true)
    canvasy += player2

    // player2 listeners
    KeyListener.onChange(Key.LEFT)  { player2 moveY 1}
    KeyListener.onChange(Key.RIGHT) { player2 moveY -1}

    // player1 scores
    var score2 = 0
    var score2_text = Text(12.5, 3, score2.toString)
    score2_text change FontSize(70)
    canvasy += score2_text

    // ball
    var ball = Circle(9, 9, 0.5)
    ball change Color("yellow")
    ball change Fill(true)
    canvasy += ball
    var direction = 0.0 // in rad
    val speed = 0.3

    atEach(50) execute {
      // collision with players
      if((ball.x + ball.radius) <= 18.5 && player2.isInside(ball.x + ball.radius)(ball.y)) {
        direction = (1 + rand.between(-0.15, 0.15)) * Math.PI
      } else if((ball.x - ball.radius) >= 0.5 && player1.isInside(ball.x - ball.radius)(ball.y)) {
        direction = rand.between(-0.15, 0.15)
      }

      // collision with left or right border
      var win = false
      if (ball.x > 19) {
        score1 += 1
        score1_text change Content(score1.toString)
        win = true
      } else if (ball.x < 0){
        score2 += 1
        score2_text change Content(score2.toString)
        win = true
      }
      if(win) {
        direction = 0.0
        ball.moveTo(9)(9)
        player1 moveYto 7.5
        player2 moveYto 7.5
      }

      // collision with up or down border
      if(ball.y < 0 || ball.y > 19){
        direction = - direction
      }

      // ball move
      ball moveX Math.cos(direction)*speed
      ball moveY -Math.sin(direction)*speed

      canvasy.draw()
    }
  }

  def snakeGame(canvas: html.Canvas): Unit = {
    val canvasy = Canvasy(canvas)
    val box = 32
    canvasy.setUnit(box)

    // background
    val ground = Image("IMG/ground.png")

    // snake
    var snake = Array(Square(9, 10, 1))
    snake change Fill(true)

    // food
    var foodX = Math.floor(Math.random() * 17 + 1).asInstanceOf[Int]
    var foodY = Math.floor(Math.random() * 15 + 3).asInstanceOf[Int]
    val food = Circle(foodX + 0.5, foodY + 0.5, 0.5)
    food change Color("blue")
    food change Fill(true)
    food change ShadowColor("Red")
    food change ShadowBlur(10)
    food change AttachImage("IMG/food.png")

    // snake listeners
    var d = "init"
    KeyListener.onChange(Key.LEFT) {if (d != "RIGHT") d = "LEFT"}
    KeyListener.onChange(Key.RIGHT) {if (d != "LEFT") d = "RIGHT"}
    KeyListener.onChange(Key.UP) {if (d != "DOWN") d = "UP"}
    KeyListener.onChange(Key.DOWN) {if (d != "UP") d = "DOWN"}

    // audio sound
    val eat = Audio("AUDIO/eat.mp3")

    // score
    var score = 0
    val scoreBoard = Text(2 , 1.6, score.toString)
    scoreBoard change Fill(true)
    scoreBoard.fontSize = 30

    val start_layout = Rectangle(5, 9, 9, 2)
    canvasy += start_layout
    start_layout change Fill(true)
    start_layout change Color("skyblue")

    val start_text = Text(6.5, 10.4, "click to start")
    start_text change FontSize(34)
    canvasy += start_text

    val finish_text = Text(6.3, 10.5, "GAME OVER")
    finish_text change Color("red")
    finish_text change FontSize(34)

    canvasy.draw()

    canvasy += Image("IMG/food.png")

    MouseListener.onChangeInside(start_layout, box) {
      MouseListener.clear()
      atEach(100) execute {
        canvasy.clear()

        canvasy.drawImage(ground,0,0)
        canvasy.draw(snake)

        //move food
        food.moveTo(foodX + 0.5)(foodY + 0.5)
        canvasy.draw(food)

        var snakeX = snake(0).x
        var snakeY = snake(0).y

        if (d eq "LEFT") snakeX -= 1
        if (d eq "UP") snakeY -= 1
        if (d eq "RIGHT") snakeX += 1
        if (d eq "DOWN") snakeY += 1

        // check if snake catch some food
        if (food.isInside(snakeX+0.5)(snakeY+0.5)) {
          score += 1
          eat.play()
          foodX = Math.floor(Math.random() * 17 + 1).asInstanceOf[Int]
          foodY = Math.floor(Math.random() * 15 + 3).asInstanceOf[Int]
        } else {
          snake = snake.take(snake.length - 1)
        }

        // update snake new head
        val newHead = Square(snakeX, snakeY, 1)
        newHead change Fill(true)

        // check if snake touch the borders
        if (snakeX < 1 || snakeX > 17  || snakeY < 3 || snakeY > 17 || snake.contains(newHead)) {
          Timer.clear()
          canvasy.draw(start_layout)
          canvasy.draw(finish_text)

          MouseListener.onChangeInside(start_layout, canvasy.unit) {
            MouseListener.clear()
            snakeGame(canvas)
          }
        }

        snake +:= newHead

        scoreBoard.txt = score.toString
        canvasy.draw(scoreBoard)
      }
    }
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
