
class NetworkEvent(object):
    pass

class ConnectEvent(NetworkEvent):
    pass

class DisconnectEvent(NetworkEvent):
    pass

class SendableEvent(NetworkEvent):
    pass

class ReceivedEvent(NetworkEvent):
    pass
