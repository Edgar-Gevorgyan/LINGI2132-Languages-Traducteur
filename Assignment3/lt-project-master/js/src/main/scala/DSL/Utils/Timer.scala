package DSL.Utils

import scala.scalajs.js.timers.RawTimers

object Timer {
  def everyInterval(interval : Double)(work : => Unit) :Unit={
    RawTimers.setInterval(() => work , interval)
  }
}
