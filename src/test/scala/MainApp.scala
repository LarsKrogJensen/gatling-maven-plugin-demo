import computerdatabase.Feeds

object MainApp extends App {
  private val feed = Feeds.events()

  println(feed)
}
