package o1.adventure

class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(actor: Player) = this.verb match
    case "go"        => Some(actor.go(this.modifiers))
    case "hunter"    => Some(actor.getHunter)
    case "help"      => Some(actor.help)
    case "get"       => Some(actor.get(this.modifiers))
    case "eat"       => Some(actor.eat(this.modifiers))
    case "basket"    => Some(actor.basket)
    case "quit"      => Some(actor.quit())
    case other       => None

  override def toString = s"$verb (modifiers: $modifiers)"

end Action

