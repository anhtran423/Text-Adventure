package o1.adventure

import scala.collection.mutable.Map

class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private var apples = 0
  private var wolves = false
  private var notification = "Hunger Points: 7/7\nType 'help' to get a list of commands and instructions.\n"

  def addApple(numberOfApples: Int): Unit = this.apples += numberOfApples

  def addWolf(): Unit = this.wolves = true

  def setNotification(text: String): Unit =
    this.notification = text

  def checkApple(numberOfApples: Int): Boolean =
    if this.apples - numberOfApples >= 0 then
      true
    else
      false

  def removeApple(numberOfApples: Int): Unit =
      this.apples -= numberOfApples

  def neighbor(direction: String) = this.neighbors.get(direction)

  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits

  def fullDescription =
    val exitList = "\n\nDirections available: " + this.neighbors.keys.mkString(" ")
    val hungerPoints = "\n\n" + this.notification
    if this.name == "Apple Garden" then
      this.name + "\n" + this.description + "\nThere are " + this.apples + " apple(s) here." + exitList + hungerPoints
    else if this.wolves then
      this.name + "\n" + this.description + "\nAAAAA, there goes a pack of wolves!!\n" + exitList + hungerPoints
    else
      this.name + "\n" + this.description + exitList + hungerPoints

  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end Area

