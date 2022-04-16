package com.example.gamesnake.network;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;


import androidx.core.app.ActivityCompat;

import com.example.gamesnake.Game;
import com.example.gamesnake.Util.DataTransmission;
import com.example.gamesnake.object.Competitor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SocketBluetooth {
    private InputStream dataInputStream;
    private OutputStream dataOutputStream;
    private int snakeID;
    private int otherSnakeID;
    private static final String serverMac = "DC:A6:32:AC:27:CB";
    private static final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice bluetoothDevice;
    private static final UUID uuid1 = UUID.fromString("d80736f6-6b2b-4ad1-b89b-bb11d6af3a84");
    private static final UUID uuid2 = UUID.fromString("d80736f6-6b2b-4ad1-b89b-bb11d6af3a83");
    private BluetoothSocket bluetoothSocket;
    private Context context;
    private Game game;

    //constructeur de classe
    public SocketBluetooth(Game game) {
        this.context = game.getContextGame();
        this.game = game;
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(serverMac);
        System.out.println("=======Client=======");
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return;
            }
            try {
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid1);
                bluetoothSocket.connect();
                System.out.println(uuid1);
            } catch (Exception e) {
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid2);
                bluetoothSocket.connect();
                System.out.println(uuid2);
            }

            dataInputStream = bluetoothSocket.getInputStream();
            dataOutputStream = bluetoothSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            bluetoothSocket.getInputStream().read(buffer);
            snakeID = Integer.parseInt(byteToStr(buffer));
            System.out.println("As snake $" + snakeID + " connected the gameServer");
        } catch (IOException e) {
            System.out.println("Error: SocketBluetooth constructor");
        }
    }


    public void update(Competitor competitor) {
        sendInformation();

        competitor.update(receiveInformation());

    }

    // envoyer des données au serveur
    public void sendInformation() {
        String jsonData = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataTransmission dtm = new DataTransmission();
            dtm.collectPositions(game.snake, game.seed);
            jsonData = mapper.writeValueAsString(dtm.pcs);

        } catch (JsonProcessingException e) {
            System.out.println("Error: gameData can't be converted to json");
        }

        try {

            dataOutputStream.write(jsonData.getBytes());
            dataOutputStream.flush();

        } catch (IOException e) {
            System.out.println("Error: can't send information");
        }


    }


    //Recevoir des données du serveur
    public String receiveInformation() {
        String res = null;
        try {
            byte[] buffer = new byte[1024];
            dataInputStream.read(buffer);
            res = byteToStr(buffer);
            game.competitor.update(res);
            System.out.println("Other Snake: " + res);

        } catch (IOException e) {
            System.out.println("Error : can't receive information");
        }
        return res;
    }

    //Créer un thread pour que le client reçoive des données
    public void startReceivingInformation(SocketBluetooth socketBluetooth) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    socketBluetooth.receiveInformation();
                }
            }
        });
        thread.start();
    }

    //Convertir la chaîne de type "byte" en classe "String"
    public String byteToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(buffer, 0, length, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }


}
