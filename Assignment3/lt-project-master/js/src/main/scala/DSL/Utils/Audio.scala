package DSL.Utils

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLAudioElement

class Audio (src: String) {
  private var loaded: Boolean = false
  val audio: HTMLAudioElement =  dom.document.createElement("audio").asInstanceOf[HTMLAudioElement]
  audio.src = src
  def play(): Unit = audio.play()
}

object Audio{
  def apply(src: String): Audio = new Audio(src)
}
