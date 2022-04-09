import struct
import threading

import bluetooth
import _thread

server_sock1 = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
server_sock1.bind(("", bluetooth.PORT_ANY))  # 绑定地址和端口
port1 = server_sock1.getsockname()[1]  # 设置端口号
server_sock1.listen(1)  # 绑定监听，最大挂起连接数为1
server_sock2 = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
server_sock2.bind(("", bluetooth.PORT_ANY))  # 绑定地址和端口
port2 = server_sock2.getsockname()[1]  # 设置端口号
server_sock2.listen(1)  # 绑定监听，最大挂起连接数为1

nbSnake = 0
msgSnake1 = ""
msgSnake2 = ""

uuid1 = "d80736f6-6b2b-4ad1-b89b-bb11d6af3a83"
uuid2 = "d80736f6-6b2b-4ad1-b89b-bb11d6af3a84"

print("=======ServerBluetooth========")


class ServersBluetoothThread(threading.Thread):

    def __init__(self, snake_sock, snakeID):
        threading.Thread.__init__(self)
        self.snakeID = snakeID
        self.snake_sock = snake_sock
        print("SNAKEID: " + str(snakeID))

    def sendMessage(self, strMes):
        try:
            self.snake_sock.send(strMes.encode())
        except:
            print("Error SendMessage")

    def run(self):
        global msgSnake1
        global msgSnake2
        try:
            strSnakeID = str(self.snakeID)
            self.snake_sock.send(strSnakeID.encode())
            while True:
                if self.snakeID == 1:
                    msgSnake1 = self.snake_sock.recv(1024).decode()
                    print(self.snake_sock.recv(1024).decode())
                    self.sendMessage(msgSnake2)

                else:
                    msgSnake2 = self.snake_sock.recv(1024).decode()
                    print(self.snake_sock.recv(1024).decode())
                    self.sendMessage(msgSnake1)

        except:
            print("Error run")


if __name__ == '__main__':
    try:
        while nbSnake < 1:
            print('Start receiving data ..... ')
            bluetooth.advertise_service(server_sock1, "GameServer", uuid1)
            snake1_sock, address = server_sock1.accept()
            nbSnake += 1
            print("Snake $" + str(nbSnake) + " connect to the serverBluetooth")
            serversBluetoothThread1 = ServersBluetoothThread(snake1_sock, nbSnake)

            bluetooth.advertise_service(server_sock2, "GameServer2", uuid2)
            snake2_sock, address = server_sock2.accept()
            nbSnake += 1
            print("Snake $" + str(nbSnake) + " connect to the serverBluetooth")
            serversBluetoothThread2 = ServersBluetoothThread(snake2_sock, nbSnake)

            try:
                serversBluetoothThread1.start()
            except:
                print("Stop 1")
            try:
                serversBluetoothThread2.start()
            except:
                print("Stop 2")



    except:
        snake1_sock.close()
        server_sock1.close()
        snake2_sock.close()
        server_sock2.close()
        print('disconnect!')
