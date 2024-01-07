package o1.adventure

class Adventure:

  val title = "Red Rinding Hood's Adventure"

  private val home = Area("Home", "East, West,home's best.")
  private val appleGarden = Area("Apple Garden", "Beautiful red apples! Why not picking some?")
  private val huntersHut  = Area("Hunter's Hut", "Argg, I am very hungry. Give me 5 apples and I will keep you safe in the woods.")
  private val forestEdge  = Area("Forest's Edge", "You are at the edge of the forest. Your journey now begins!")
  private val flowerForest = Area("Flower Forest", "The scenery is breathtaking. Who would ever be able to resist this beauty?")
  private val mangroveSwamp = Area("Mangrove Swamp", "Ew, frogs!")
  private val birchForest = Area("Birch Forest", "So many beautiful birch trees.")
  private val oldGrowthPineTaiga = Area("Old Growth Pine Taiga", "The dense pine taiga is blocking out the sunlight! It's getting scarier somehow...")
  private val darkForest = Area("Dark Forest", "Careful, you are very deep in the forest now!")
  private val windsweptForest = Area("Windswept Forest", "The windswept trees look so artistic... Yet, the scary feeling hasn't gone all away!")
  private val sunflowerPlains = Area("Sunflower Plains", "Mesmerizing yellow flowers! You're almost there!!")
  private val grammasHouse  = Area("Gramma's House", "Welcome my grandchild!")
  private val destination = grammasHouse

  home     .setNeighbors(Vector("north" -> appleGarden, "east" -> forestEdge, "south" -> huntersHut))
  appleGarden.setNeighbors(Vector("south" -> home))
  huntersHut.setNeighbors(Vector("north" -> home))
  forestEdge   .setNeighbors(Vector("north" -> flowerForest, "east" -> mangroveSwamp, "west" -> home))
  flowerForest   .setNeighbors(Vector("east" -> birchForest, "south" -> forestEdge))
  birchForest   .setNeighbors(Vector("south" -> mangroveSwamp, "west" -> flowerForest))
  mangroveSwamp   .setNeighbors(Vector("north" -> birchForest, "west" -> forestEdge, "south" -> oldGrowthPineTaiga))
  oldGrowthPineTaiga   .setNeighbors(Vector("north" -> mangroveSwamp, "east" -> darkForest))
  darkForest   .setNeighbors(Vector("north" -> windsweptForest, "west" -> oldGrowthPineTaiga, "east" -> sunflowerPlains))
  windsweptForest   .setNeighbors(Vector("south" -> darkForest))
  sunflowerPlains   .setNeighbors(Vector("north" -> grammasHouse, "west" -> darkForest))
  grammasHouse   .setNeighbors(Vector("south" -> sunflowerPlains))

  birchForest.addWolf()
  darkForest.addWolf()

  appleGarden.addApple(40)

  val player = Player(home)

  def isComplete = this.player.location == this.destination && this.player.inventory >= 9 && this.player.hungerPoints != 0

  def isOver = this.isComplete ||
    this.player.hasQuit ||
    this.player.hungerPoints == 0 ||
    (this.player.inventory < 9 && this.player.location == this.destination) ||
    (Vector("Birch Forest", "Dark Forest").contains(this.player.location.name) && !this.player.hasHunter)

  def welcomeMessage = "You are at home. It's nice to relax and stay at a safe place.\n\nHowever, Gramma has caught a cold and is longing for some apples. You need to go get her 9 apples!"

  def goodbyeMessage =
    if this.isComplete then
      "Thank you for going such a long way to give me 9 apples. I really appreciate it."
    else if this.player.hungerPoints == 0 then
      "You died of hunger!\nGame over!"
    else if this.player.inventory < 9 && this.player.location == this.destination then
      "You don't have enough apples for gramma. She is not happy!\nGame over!"
    else if Vector("Birch Forest", "Dark Forest").contains(this.player.location.name) && !this.player.hasHunter then
      "The wolves attacked you and you have no hunter to lean on!\nGame over!"
    else  // game over due to player quitting
      "That's fine. Let's play again!"

  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      if Vector("Birch Forest", "Dark Forest").contains(this.player.location.name) && this.player.hasHunter then
        this.player.location.setNotification("Luckily, you have a hunter with you and he takes care of the situation.\nHunger Points: " + this.player.hungerPoints + "/7\nType 'help' to get a list of commands.\n")
      else
        this.player.location.setNotification("Hunger Points: " + this.player.hungerPoints + "/7\nType 'help' to get a list of commands and instructions.\n")
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")

end Adventure

