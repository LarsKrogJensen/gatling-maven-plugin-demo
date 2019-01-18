package computerdatabase

import spray.json._
import DefaultJsonProtocol._
import scalaj.http.{Http, HttpResponse}

import scala.collection.mutable.ArrayBuffer

object Feeds {

  def extractEventIds(map: Map[String, Any]): Array[Map[String, AnyVal]] = {
    val data = ArrayBuffer[Map[String, AnyVal]]()

    val events = map.get("events").get

    //    events.foreach(eventWithBetOffer => {
    //      val event: Map[String, Any] = eventWithBetOffer.get("event").get asInstanceOf
    ////      println(s"Id: ${event.get("id")}")
    //    })

    data.toArray
  }

  def events(): Array[Map[String, AnyVal]] = {
    val response: HttpResponse[String] = Http("https://eu-offering.kambicdn.org/offering/v2018/ubse/listView/football/england/fa_cup.json?lang=sv_SE&market=SEuseCombined=false")
      .asString

    val json: JsObject = response.body.parseJson asJsObject

    val events: JsArray = json.fields.getOrElse("events", JsArray.empty).asInstanceOf[JsArray]

    events.elements.map(evBo => {
      val event: JsObject = evBo.asJsObject.fields.getOrElse("event", JsObject()).asJsObject
      val tags: JsArray = event.fields.getOrElse("tags", JsArray.empty).asInstanceOf[JsArray]
      val eventId = event.fields.get("id").get
      println(s"Id $eventId tags $tags")
      eventId
    })


//    json match {
//      case Some(map: Map[String, Any]) => extractEventIds(map)
//      case None => throw new RuntimeException("Invalid auth respnse")
//      case _ => Array()
//    }

    Array()
  }


}
