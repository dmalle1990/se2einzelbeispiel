package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {
    //server connect
    private final String serverDomain = "se2-submission.aau.at";
    private final int port = 20080;
    //stuff for xml
    private TextView serverResponse;
    private EditText inputNumber;
    private String matriculationNumber;
    private TextView resultCalc;
    private Button btn;
    private Button btnCalc;
    //reader and writer
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find the reference in the activity main xml
        inputNumber = findViewById(R.id.editTextNumber);
        btn = findViewById(R.id.button);
        serverResponse = findViewById(R.id.serverResponseTextView);
        btnCalc = findViewById(R.id.calculate);
        resultCalc = findViewById(R.id.responseCalculation);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matriculationNumber = inputNumber.getText().toString();
                //TextView serverResponse = findViewById(R.id.serverResponseTextView);
                //serverResponse = findViewById(R.id.serverResponseTextView);
                //serverResponse.setText("klick");
                //serverResponseTextView == helloworld aus dem tutorium

                Thread t1 = new Thread (new Runnable () {
                    public void run() {
                            try {
                                Socket clientSocket = new Socket(serverDomain, port);

                                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                                bufferedWriter.write(matriculationNumber);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();

                                String response = bufferedReader.readLine();

                                runOnUiThread(new Runnable() {
                                    public void run () {
                                        serverResponse.setText(response);
                                    }
                                });

                                bufferedWriter.close();
                                bufferedReader.close();
                                clientSocket.close();

                            } catch (UnknownHostException e) {
                                Log.e("err", "server not found" + e.getMessage());
                            } catch (IOException e) {
                                Log.e("err", "I.O.error" + e.getMessage());
                            }
                        }

                });
                t1.start();


                try {
                    t1.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // und vll Host exception hinzufügen...

            }
        });

        //nächster button


        btnCalc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                matriculationNumber = inputNumber.getText().toString();

                // code ab hier
                int alternatingSum = Math.calculate(matriculationNumber);
                String resultFromCalc = Math.getResult(alternatingSum);

                //
                //resultFromCalc müsste dem TextView resultCalc zugewiesen werden
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultCalc.setText(resultFromCalc);
                    }

                    /*
                    * runOnUiThread(new Runnable() {
                                    public void run () {
                                        serverResponse.setText(response);
                                    }
                                });*/
                });







            }
        });



    }

    /*public void onClick(View view) {
        if (view.getId() == R.id.button) {

        }

    }*/

    /*
    new Thread(new Runnable () {
                    public void run() {
                        try {
                            Socket clientSocket = new Socket(serverDomain, port);

                            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                            bufferedWriter.write(matriculationNumber);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();

                            String responseFromServer = bufferedReader.readLine();

                            //update UI
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    serverResponse.setText(responseFromServer);
                                }
                            });

                            bufferedWriter.close();
                            bufferedReader.close();
                            clientSocket.close();


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }).start();
    * */



    //button.setOnClickListener(new View.OnClickListener()) {





    //button set on click listener...

    //onClick ... wäre die funktion --> google

    //thread in eigene klasse

    //Thread t1 = new Thread (new Client) {

    //t1.start();



}