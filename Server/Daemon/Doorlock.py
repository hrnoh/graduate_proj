
class Doorlock(object):
    def __init__(self, name, sock, addr):
        self.name = name
        self.sock = sock
        self.addr = addr

    def getName(self):
        return self.name

    def getSock(self):
        return self.sock

    def getAddr(self):
        return self.addr