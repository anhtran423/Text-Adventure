package o1.adventure


class Player(startingArea: Area):

  private var currentLocation = startingArea
  private var quitCommandGiven = false
  var inventory = 0
  var hungerPoints = 7
  var hasHunter = false

  def hasQuit = this.quitCommandGiven

  def location: Area = this.currentLocation

  def getHunter: String =
    if this.currentLocation.name == "Hunter's Hut" && inventory >= 5 && !hasHunter then
      hasHunter = true
      inventory -= 5
      "You successfully hired the hunter with 5 apples!"
    else if inventory < 5 && this.currentLocation.name == "Hunter's Hut" && !hasHunter then
      "You don't have enough apples. Need " + (5 - inventory) + " more!"
    else if hasHunter then
      "You already have a hunter!"
    else
      "You can only hire the hunter at the Hunter's Hut!"

  def go(direction: String): String =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then
      this.hungerPoints -= 1
      "You go " + direction + "."
    else
      "You can't go " + direction + "."

  def quit(): String =
    this.quitCommandGiven = true
    ""

  def eat(noApples: String): String =
    if noApples.forall(Character.isDigit) then
      var numberOfApples = noApples.toInt
      if numberOfApples > inventory then
        "You don't have that many apples!"
      else if numberOfApples + hungerPoints > 7 then
        "You cannot eat that much! You will be too full to continue the journey!"
      else
        hungerPoints += numberOfApples
        inventory -= numberOfApples
        "Successfully ate " + numberOfApples + " apple(s)!"
    else
      "Invalid input. Please put in an integer."

  def get(noApples: String): String =
    if noApples.forall(Character.isDigit) then
      var numberOfApples = noApples.toInt
      if this.location.name == "Apple Garden" then
        if this.location.checkApple(numberOfApples) && (numberOfApples + this.inventory <= 10) then
          this.location.removeApple(numberOfApples)
          this.inventory += numberOfApples
          "You pick " + numberOfApples + " apples from the garden."
        else
          "Your basket can only carry 10 apples at most."
      else
        "You can only pick apples from the Apple Garden."
    else
      "Invalid input. Please put in an integer."

  def basket =
    if this.inventory == 0 then
      "You have no apple :("
    else
      s"You are carrying:\n${this.inventory} apple(s)."

  def help = "Commands:\n" +
    "go [direction]: Travelling between areas. You can go north, east, west and south depending on the area you are in. Each go command is equivalent to 1km in the game world.\n\n" +
    "eat [number]: Eat apples to restore your hunger points. Don't starve yourself! But don't eat too much either; you can only eat until your hunger points are full.\n\n" +
    "get [number]: Pick apples from the Apple Garden only. Remember that your basket can carry 10 apples at most, and gramma wants 9 apples at least. Picking the apples is the requirement to restore hunger points, ask for help from the hunter, and visit gramma.\n\n" +
    "basket: Check the number of apples you are carrying.\n\n" +
    "hunter: Hire the hunter with 5 apples to keep you safe while passing through dangerous areas.\n\n" +
    "quit: Quit the game.\n\n" +
    "Instructions:\n" +
    "You need to bring at least 9 apples to gramma without getting attacked by the wolves or dying of hunger. In order to have the apples, you need to find the Apple Garden since the apples can only be picked there. In order to ask for help from the hunter to keep you safe from the wolves, you need to find the Hunter's Hut and have at least 5 apples in your basket. In order to give gramma 9 apples, you need to find the quickest possible way to Gramma's House. Travelling between each area will lose you 1 hunger point, and in order to restore the hunger points, you need to eat apples (1 apple equals 1 hunger point). Use the above commands in appropriate situations."

  override def toString = "Now at: " + this.location.name

end Player

