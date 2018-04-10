package com.example.sara.billards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.sara.billards.Prices.AllPrices;
import com.example.sara.billards.Prices.DefaultPricesRepository;
import com.example.sara.billards.registration.LoginActivity;
import com.example.sara.billards.registration.User_reg;
import com.example.sara.billards.tables.DefaultTablesRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;


public class MainActivity extends Activity {
    private static final String TAG = "MyActivity";

    Button binf, blogin, bregister, bprices, brez;
    private RequestQueue mQueue, mQueue2;
    Context context;
    static int size,size2;
    static String dane,dane2;
    @Override
    public void onBackPressed() {
        if (LoginActivity.logged > 0) {
            this.finishAffinity();
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binf = (Button) findViewById(R.id.binf);
        blogin = (Button) findViewById(R.id.blogin);
        bregister = (Button) findViewById(R.id.brej);
        bprices = (Button) findViewById(R.id.bprices);
        brez = (Button) findViewById(R.id.brez);

        final View[] a = {findViewById(R.id.brej)};
        View b = findViewById(R.id.brez);
        View c = findViewById(R.id.blogin);
        View d = findViewById(R.id.bpanel);
        // LoginActivity.logged=4; //TYLKO DO EKSPERYMENTÓW
        if (LoginActivity.logged == 0) {
            a[0].setVisibility(View.VISIBLE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.GONE);
        }
        if (LoginActivity.logged > 0) {
            a[0].setVisibility(View.GONE);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.VISIBLE);


        }

        JSONArray jsonArray = new JSONArray();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        String url = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api4/";
        DefaultTablesRepository.createSingletonInstance(mQueue, url);
        DefaultTablesRepository.getInstance().getTables(
                Tables -> {
                    size = Tables.size();
                    dane = (Tables.toString());

                },
                error -> {
                });

        try {

            String PriceUrl = "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api3/";
            DefaultPricesRepository.createSingletonInstanceForPrices(mQueue2, PriceUrl); //teraz MQueue2 jest pusta ale w tym miejscu sie wywala
            DefaultPricesRepository.getInstance().getPrices(AllPrices -> {               //gdyby dac jako argument mQueue to metoda GET dziala ale cala  apka sie wywali
                        size2 = AllPrices.size();
                        dane2 = AllPrices.toString();
                    },
                    error -> {
                        Log.e(TAG, " connect with Price's server " + error);

                    });


        } catch (Exception e) {
            Log.e(TAG, " error in PricesAct " + e);
        }

        binf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, Inf.class);
                startActivity(intent);
            }
        });

        brez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context = getApplicationContext();


                Intent intent = new Intent(context, After_registration.class);
                intent.putExtra("size", size);

                startActivity(intent);

            }
        });


        bprices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, PricesAct.class);
                startActivity(intent);

            }
        });


        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, User_reg.class);
                startActivity(intent);
            }
        });

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });


    }






}
//        for (int i=0;i<= t.length;i++){
//            System.out.println("tablica" );
//
//            t[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context = getApplicationContext();
//        Intent intent = new Intent(context, Registration.class);
//        startActivity(intent);
//                }
//            });
//        }
//    }}

//
