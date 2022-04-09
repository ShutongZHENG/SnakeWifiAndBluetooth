import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class GameServer {
    private ServerSocket listener;
    private int nbSnake;
    private static final int PORT = 2333;
    private ServersThread snake1thread;
    private ServersThread snake2thread;
    private String msgSnake1;
    private String msgSnake2;
//
//    //测试
//     private int[] value;
//     private int turensMade;
//     private int maxTurns;
//
//
//    //

    public GameServer() {
//        //测试
//        turensMade = 0;
//        maxTurns = 4;
//        value = new int[4];
//        for (int i =0 ;i <4; i++){
//            value[i] = (int) (Math.random()*100);
//            System.out.println("Value #"+i+" "+value[i]);
//        }
//
//
//
//
//        //
        System.out.println("=======GameServer=====");
        nbSnake = 0;
        try {
            listener = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Error: GameServer Constructor");
        }
    }

    public void ListenConnections() {
        try {
            System.out.println("search connections-------");
            while (nbSnake < 2) {
                Socket client = listener.accept();
                nbSnake += 1;
                System.out.println("Snake $" + nbSnake + " connect the server");
                ServersThread serversThread = new ServersThread(client, nbSnake);
                if (nbSnake == 1){
                    snake1thread = serversThread;
                }else {
                    snake2thread = serversThread;
                }
//                Thread thread = new Thread(serversThread);
//                thread.start();
            }
            System.out.println("2 snakes are already");
            Thread thread1 = new Thread(snake1thread);
            Thread thread2 = new Thread(snake2thread);
            thread1.start();
            thread2.start();
        } catch (IOException e) {
            System.out.println("Error: ListenConnections");
        }

    }

    private class ServersThread implements Runnable {
        private Socket socket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private int snakeID;

        public ServersThread(Socket socket, int snakeID) {
            this.socket = socket;
            this.snakeID = snakeID;
            try {
                dataInputStream = new DataInputStream(this.socket.getInputStream());
                dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Error: ServersThread " + snakeID);
            }
        }


        @Override
        public void run() {
            try {
                dataOutputStream.writeInt(snakeID);
//                //测试
//                dataOutputStream.writeInt(maxTurns);
//                dataOutputStream.writeInt(value[0]);
//                dataOutputStream.writeInt(value[1]);
//                dataOutputStream.writeInt(value[2]);
//                dataOutputStream.writeInt(value[3]);
//                //
//                dataOutputStream.flush();
//                //测试
//                try {
//                    sleep(5000);
//                }catch (InterruptedException e ){
//                    System.out.println("Error: sleep");
//                }

                //
                while (true) {
                    //  System.out.println("sends kaishi ");
                    if (snakeID == 1){
                      //  msgSnake1 = String.valueOf(dataInputStream.readInt());
                        msgSnake1 = dataInputStream.readUTF();
                        System.out.println("Snake $1 sends "+msgSnake1);
                       // snake2thread.sendMessage(Integer.parseInt(msgSnake1));
                        snake2thread.sendMessage(msgSnake1);
                    }else {
                      //  msgSnake2 = String.valueOf(dataInputStream.readInt());
                        msgSnake2 = dataInputStream.readUTF();
                        System.out.println("Snake $2 sends "+msgSnake2);
                       // snake1thread.sendMessage(Integer.parseInt(msgSnake2));
                        snake1thread.sendMessage(msgSnake2);
                    }

                }
            } catch (IOException e) {
            System.out.println("Error: Thread " + snakeID+" run");

            }
        }

        public void sendMessage(int n){
            try {
                dataOutputStream.writeInt(n);
                dataOutputStream.flush();
            }catch (IOException e){
                System.out.println("Error: sendmessage Sever");
            }
        }
        public void sendMessage(String str){
            try {
                dataOutputStream.writeUTF(str);
                dataOutputStream.flush();
            }catch (IOException e){
                System.out.println("Error: sendmessage Sever");
            }
        }



    }


    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.ListenConnections();
    }

}
