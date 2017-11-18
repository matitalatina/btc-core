package bitcoins.actors

import javax.inject.Inject

import akka.actor.{Actor, ActorRef, Props}
import bitcoins.actors.UpdateRoomActor.{BroadcastUpdate, RegisterSocket, UnRegisterSocket}

object UpdateRoomActor {
  def props: Props = Props[UpdateRoomActor]

  final val name = "btc-update-notifier"

  sealed trait Message

  case class BroadcastUpdate(currencyCode: String) extends Message

  case class RegisterSocket(out: ActorRef) extends Message

  case class UnRegisterSocket(out: ActorRef) extends Message

}

class UpdateRoomActor @Inject()() extends Actor {

  var sockets: List[ActorRef] = List[ActorRef]()

  def sendToAll(currencyCode: String): Unit = {
    sockets.foreach(_ ! ListenerSocketActor.SendToClient(currencyCode))
  }

  override def receive: PartialFunction[Any, Unit] = {
    case RegisterSocket(socket) =>
      println("Welcome " + socket)
      sockets = socket :: sockets

    case UnRegisterSocket(socket) =>
      sockets = sockets.filterNot(_ == socket)

    case BroadcastUpdate(currencyCode: String) => sendToAll(currencyCode)
  }
}
