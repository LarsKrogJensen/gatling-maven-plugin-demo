package computerdatabase

import scalaj.http.{Http, HttpResponse}
import spray.json.DefaultJsonProtocol._
import spray.json._

object Feeds {

  def extractEventId(json: JsValue): Option[JsValue] = {
    val event: JsObject = json.asJsObject.fields.getOrElse("event", JsObject()).asJsObject
    val tags: JsArray = event.fields.getOrElse("tags", JsArray.empty).asInstanceOf[JsArray]
    val eventId = event.fields.get("id")
    println(s"Id $eventId tags $tags")
    eventId
  }

  def events(): Array[Map[String, AnyVal]] = {
    val response: HttpResponse[String] = Http("https://eu-offering.kambicdn.org/offering/v2018/ubse/listView/football/england/fa_cup.json?lang=sv_SE&market=SEuseCombined=false")
      .asString

    val json: JsObject = response.body.parseJson.asJsObject

    val events: JsArray = json.fields.getOrElse("events", JsArray.empty).asInstanceOf[JsArray]

    val eventIds = events.elements.map(evBo => extractEventId(evBo))
      .filter(eventId => eventId.nonEmpty)
      .map(eventId => Map("eventId" -> eventId.get.convertTo[Int]))

    eventIds.toArray
  }

}
