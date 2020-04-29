package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLAudioElement

class Audio (src: String) {
  val audio: HTMLAudioElement =  dom.document.createElement("audio").asInstanceOf[HTMLAudioElement]
  audio.src = src
  def play(): Unit = audio.play()
  def pause(): Unit = audio.pause()
}

object Audio{
  def apply(src: String): Audio = new Audio(src)
}
