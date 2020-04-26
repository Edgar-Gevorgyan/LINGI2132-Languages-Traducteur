package DSL.Utils

import scala.scalajs.js.timers.{RawTimers, SetIntervalHandle, SetTimeoutHandle}

object Timer{
  def remove(handle: SetIntervalHandle): Unit = RawTimers.clearInterval(handle)
  def remove(handle: SetTimeoutHandle): Unit = RawTimers.clearTimeout(handle)
}

class atEach(interval : Double){
  def execute(work : => Unit) :SetIntervalHandle = RawTimers.setInterval(() => work , interval)
}
object atEach{
  def apply(interval : Double): atEach = new atEach(interval)
}

class after(timeout : Double){
  def execute(work : => Unit) :SetTimeoutHandle = RawTimers.setTimeout(() => work , timeout)
}

object after{
  def apply(timeout: Double): after = new after(timeout : Double)
}