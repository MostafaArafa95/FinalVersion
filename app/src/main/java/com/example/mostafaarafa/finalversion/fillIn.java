package com.example.mostafaarafa.finalversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

import Encryptions.Client;

public class fillIn extends AppCompatActivity {
    final private static String Encryptions[]={"playfair","railfence","rowtransposition","vigenere","monoalphabetic","aes","caesar"};
    static private String key="";
    static private String encryptionType="";

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        fillIn.key = key;
    }



    public static String getEncryptionType() {
        return encryptionType;
    }

    public static void setEncryptionType(String encryptionType) {
        fillIn.encryptionType = encryptionType;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in);
        DropDownSetup();
    }


    private void DropDownSetup(){

        Spinner spinner = (Spinner) findViewById(R.id.EncryptionType);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Encryptions); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


    }



    public void connect(View v){
        final String ip=((EditText)findViewById(R.id.ipText)).getText().toString();
        final Intent intent=new Intent(this,MainActivity.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.connectToClient(ip,12345);
                    Log.d("connection","connected to "+ip);
                    setEncryptionType(((Spinner)findViewById(R.id.EncryptionType)).getSelectedItem().toString());
                    setKey(((EditText)findViewById(R.id.key)).getText().toString());
                    startActivity(intent);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void waitForConnection(View v){
        final Intent intent=new Intent(this,MainActivity.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.waitForConnection();
                    Log.d("connection","connected  ");
                    setEncryptionType(((Spinner)findViewById(R.id.EncryptionType)).getSelectedItem().toString());
                    setKey(((EditText)findViewById(R.id.key)).getText().toString());
                    startActivity(intent);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }


}
