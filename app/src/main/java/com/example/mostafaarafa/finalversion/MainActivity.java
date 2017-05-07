package com.example.mostafaarafa.finalversion;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Encryptions.Client;
import Encryptions.CryptographyManager;
import Encryptions.NoConnectionException;

public class MainActivity extends AppCompatActivity {
    final private static String Encryptions[]={"playfair","railfence","rowtransposition","vigenere","monoalphabetic","aes","caesar"};
    ScrollView mainScrollView;
    RelativeLayout mainRelativeLayout;
    LinearLayout lastLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init variables
        initValues();



        //button setup
        final EditText text=(EditText) findViewById(R.id.sendingText);

        ImageButton button=(ImageButton) findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                //Log.d("send", "send: hi ");

                send(text.getText().toString());
                text.setText("");
            }
        });

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.EncType);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Encryptions); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


        //setting up

        try {
            Client.startReciving(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }


    }


    private void initValues(){
        mainScrollView=(ScrollView)findViewById(R.id.mainScrollView);
        mainRelativeLayout=(RelativeLayout)findViewById(R.id.mainRelativeLayout);
        lastLinearLayout=null;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addText(String sendingText,boolean sending,String textType){
        //LinearLayout setup
        LinearLayout l=new LinearLayout(this);
        l.setId(View.generateViewId());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int topmargin=0;
        if(lastLinearLayout!=null){
            //p.addRule(RelativeLayout.BELOW, R.id.below_id);
            topmargin=30;
            p.addRule(RelativeLayout.BELOW,lastLinearLayout.getId());
            Log.d("ssssss", "send: hi");
        }
        if(sending){
            p.setMargins(0,topmargin,40,0);
        }else{
            p.setMargins(40,topmargin,0,0);

        }
        l.setLayoutParams(p);
        l.setOrientation(LinearLayout.VERTICAL);
        lastLinearLayout=l;

        //textView setup
        TextView tv=new TextView(this);
        RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p2.topMargin=10;
        if (sending) {
            tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        }else
            tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner1));
        tv.setTextSize(30);
        tv.setText(textType+" :"+ sendingText);



        //adding components
        l.addView(tv);
        mainRelativeLayout.addView(l);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void send(final String sendingText){
        addText(sendingText,true,"Plain Text");
        String encryptedText="";

        try {
            encryptedText= CryptographyManager.encrypt(fillIn.getKey(), sendingText, fillIn.getEncryptionType());
        }catch (Exception e){
            addText("Text can only contain Arabic/English alphabets ",true,"Invalid Input");
            return;
        }
        addText(encryptedText,true,"Encrypted");
        final String finalEncryptedText = encryptedText;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.send(finalEncryptedText);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void receive(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addText(msg,false,"Encrypted");
                try {
                    addText(CryptographyManager.decrypt(fillIn.getKey(),msg,fillIn.getEncryptionType()),false,"Decrypted");
                } catch (Exception e){
                    //addText("Invalid",false,"Decrypted");

                }

            }
        });


    }

    public void changeEncryptionType(View v){

        fillIn.setKey(((EditText)findViewById(R.id.EncKey)).getText().toString());
        fillIn.setEncryptionType(((Spinner)findViewById(R.id.EncType)).getSelectedItem().toString());
    }

}
