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


        //send button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matriculationNumber = inputNumber.getText().toString();

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


        //nächster button - calculate
        btnCalc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                matriculationNumber = inputNumber.getText().toString();

                // code ab hier
                int alternatingSum = Math.calculate(matriculationNumber);
                String resultFromCalc = Math.getResult(alternatingSum);
                String addingSentence = "\nund hat den Wert " + alternatingSum + " ";
                String concatenated = resultFromCalc + addingSentence;

                //
                //resultFromCalc müsste dem TextView resultCalc zugewiesen werden
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultCalc.setText(concatenated);
                    }

                });

            }
        });



    }



}