package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {

    String order;
    String[] items;
    String[] itemDetails;

    TextView orderText;
    EditText address;
    Button sendOrder;
    Button shareOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);

        orderText = (TextView) findViewById(R.id.orderText);
        address = (EditText) findViewById(R.id.address);
        sendOrder = (Button) findViewById(R.id.sendOrder);
        shareOrder = (Button) findViewById(R.id.shareOrder);

        StringBuilder sb = new StringBuilder();

        order = getIntent().getStringExtra("order");
        items = order.split(":");

        System.out.println(order);
        for(String item : items) {
            System.out.println(item);
            itemDetails = item.split(";");
            sb.append(itemDetails[0]);
            sb.append(", ");
            sb.append(itemDetails[1]);
            sb.append(" lei");
            sb.append("\n");
        }

        String singleString = sb.toString();
        orderText.setText(singleString);


        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderAddress = address.getText().toString();

                /*
                    ADD TO DATABASE
                 */

                Intent i = new Intent(Order.this, HomeActivity.class);
                startActivity(i);
            }
        });

        shareOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, orderText.getText().toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }
}














