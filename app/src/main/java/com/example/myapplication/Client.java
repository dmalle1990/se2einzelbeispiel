package com.example.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable {

    //declarations
    private static String serverDomain = "se2-submission.aau.at";
    //private String port = "20080";
    private static int port = 20080;

    private String matricleNumber;
    private Socket clientSocket;
    private BufferedWriter output;
    private BufferedReader input;

    private MainActivity mainActivity;

    //constructor
    public Client(String matricleNumber) {
        this.matricleNumber = matricleNumber;
        this.mainActivity = mainActivity;
    }

    public void run () {
        try {
            clientSocket = new Socket(serverDomain, port);
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String serverResponse = input.readLine();

            //closing everything, when it is not needed any more
            output.close();
            input.close();
            clientSocket.close();

            //
            /*mainActivity.runOnUiThread(new Runnable() {
                public void run() {
                    mainActivity.updateServerResponse(serverResponse);
                }
            })*/
        }
        //error handling
        catch (IOException e){
            e.printStackTrace();

        }
    }

    //public void run () {


        //variablen für den server
        //port und ip
        //socket
        //input and output buffered Reader udn Writer
        //variable für answer from server


        //variable to send to server
        //im constructor die variable übergeben // also für den Client einen Constructor machen



        /*
    @Override
    public void run() {
        //hier den client to server request
        //handling mit try and catch
    }
    */
    //}

}
