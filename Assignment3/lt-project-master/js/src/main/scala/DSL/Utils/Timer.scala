package DSL.Utils

import scala.scalajs.js.timers.{RawTimers, SetIntervalHandle, SetTimeoutHandle}


/**
 *  This object allows to remove function related to listeners
 */
object Timer {
  var stackIntervalHandle: List[SetIntervalHandle] = List()
  var stackTimeoutHandle: List[SetTimeoutHandle] = List()
  def remove(handle: SetIntervalHandle): Unit = RawTimers.clearInterval(handle)
  def remove(handle: SetTimeoutHandle): Unit = RawTimers.clearTimeout(handle)
  def clear(): Unit = {
    stackIntervalHandle.foreach(x => remove(x))
    stackTimeoutHandle.foreach(x => remove(x))
  }
}

/**
 * @param interval is the interval between each execution of 'work' function
 * The only usage of this class with his object is to schedule the execution of a function in a beautiful way
 * example : atEach(100) execute print(10)
 */
class atEach(interval: Double) {
  def execute(work: => Unit): SetIntervalHandle = {
    val tmp = RawTimers.setInterval(() => work, interval)
    Timer.stackIntervalHandle = tmp :: Timer.stackIntervalHandle
    tmp
  }
}
/**
 * This object only has an apply function to remove the 'new' keyword during creation of an instance of atEach
 * Thus this object allows the usage of atEach('interval') execute print('work')
 */
object atEach {
  def apply(interval: Double): atEach = new atEach(interval)
}

/**
 * @param timeout is the time after which the 'work' function is executed
 * The only usage of this class with his object is to schedule the execution of a function in a beautiful way
 * example : after(100) execute print(10)
 **/
class after(timeout: Double) {
  def execute(work: => Unit): SetTimeoutHandle = {
    val tmp = RawTimers.setTimeout(() => work, timeout)
    Timer.stackTimeoutHandle = tmp :: Timer.stackTimeoutHandle
    tmp
  }
}

/**
 * This object has only apply function to remove the 'new' keyword during creation of an instance of after
 * Thus this object allow the usage of after('timeout') execute 'work'
 */
object after {
  def apply(timeout: Double): after = new after(timeout: Double)
}