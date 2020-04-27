package DSL.Utils

import scala.scalajs.js.timers.{RawTimers, SetIntervalHandle, SetTimeoutHandle}

object Timer{
  var stackIntervalHandle: List[SetIntervalHandle] = List()
  var stackTimeoutHandle: List[SetTimeoutHandle] = List()
  def remove(handle: SetIntervalHandle): Unit = RawTimers.clearInterval(handle)
  def remove(handle: SetTimeoutHandle): Unit = RawTimers.clearTimeout(handle)
  def remove(): Unit ={
    stackIntervalHandle.foreach(x => remove(x))
    stackTimeoutHandle.foreach(x => remove(x))
  }
}

class atEach(interval: Double){
  def execute(work: => Unit): SetIntervalHandle = {
    val tmp = RawTimers.setInterval(() => work , interval)
    Timer.stackIntervalHandle = tmp :: Timer.stackIntervalHandle
    tmp
  }
}
object atEach{
  def apply(interval: Double): atEach = new atEach(interval)
}

class after(timeout: Double){
  def execute(work: => Unit) :SetTimeoutHandle = {
    val tmp = RawTimers.setTimeout(() => work , timeout)
    Timer.stackTimeoutHandle = tmp :: Timer.stackTimeoutHandle
    tmp
  }
}

object after{
  def apply(timeout: Double): after = new after(timeout: Double)
}