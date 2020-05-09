package DSL.Utility

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLAudioElement


/**
 * @param src the source where the audio sound is stored
 *            example : Audio("AUDIO/eat.mp3") the eat.mp3 is stored in the AUDIO folder of this project
 */
class Audio (src: String) {
  val audio: HTMLAudioElement =  dom.document.createElement("audio").asInstanceOf[HTMLAudioElement]
  audio.src = src

  /**
   *  play the audio sound
   */
  def play(): Unit = audio.play()
  /**
   *  stop the audio sound if it is playing
   */
  def pause(): Unit = audio.pause()
}

object Audio{
  /**
   *
   * @param src the source where the audio sound is stored
   * @return this allow to creat an Audio object without the 'new' keyword
   */
  def apply(src: String): Audio = new Audio(src)
}
