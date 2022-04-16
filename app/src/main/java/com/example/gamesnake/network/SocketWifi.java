package com.example.gamesnake.network;

import android.widget.Space;

import com.example.gamesnake.Game;
import com.example.gamesnake.Util.DataTransmission;
import com.example.gamesnake.object.Competitor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWifi {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private  String ipServer ;
    private  int portServer;
    private int snakeID;
    private  int otherSnakeID;

    private Game game;
 //constructeur de classe
    public SocketWifi(String ipServer , int portServer, Game game){
        this.ipServer = ipServer;
        this.portServer =portServer;
        this.game = game;

        System.out.println("===Client===");
        try {
            socket = new Socket(ipServer,portServer);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            snakeID = dataInputStream.readInt();
            System.out.println("As snake $"+snakeID+" connected the gameServer");
        }catch (IOException e ){
            System.out.println("Error: Client Constructor");
        }


    }
    public void update(Competitor competitor)  {
        sendInformation();

        competitor.update(receiveInformation());

    }
    // envoyer des données au serveur
    public void sendInformation()  {
        String jsonData = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataTransmission dtm = new DataTransmission();
            dtm.collectPositions(game.snake, game.seed);
             jsonData =  mapper.writeValueAsString(dtm.pcs);

        }catch (JsonProcessingException e){
            System.out.println("Error: gameData can't be converted to json");
        }

        try {
            dataOutputStream.writeUTF(jsonData);
            dataOutputStream.flush();

        }catch (IOException e) {
             System.out.println("Error: can't send information");
        }


    }


    //Recevoir des données du serveur
    public String receiveInformation() {
        String res =null;
        try {
            res = dataInputStream.readUTF();
            game.competitor.update(res);
            System.out.println("Other Snake: "+ res);

        }catch (IOException e) {
            System.out.println("Error : can't receive information");
        }
        return res;
    }


    //Créer un thread pour que le client reçoive des données
    public void startReceivingInformation(SocketWifi socketWifi){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    socketWifi.receiveInformation();
                }
            }
        });
        thread.start();
    }










}
