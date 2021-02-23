package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemActivity extends AppCompatActivity {

    ImageView img;
    TextView textView,priceText,notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        img=(ImageView)findViewById(R.id.recipe_img);
        textView=(TextView)findViewById(R.id.recipe_text);
        priceText=(TextView)findViewById(R.id.preiceTextView);
        notes=findViewById(R.id.notes);
        String note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam non scelerisque ipsum." +
                " Praesent aliquam augue dolor, vel mattis enim tempus quis. Phasellus iaculis, ante et varius venenatis, " +
                "est augue fringilla magna, at iaculis magna elit eu sapien. Sed eu volutpat elit, sollicitudin molestie elit." +
                " Nam finibus est eu erat aliquam, non porta lorem lobortis. Morbi vitae accumsan massa. Donec egestas, nisi nec placerat maximus, " +
                "sapien odio pellentesque ante, eget ornare tortor mi a mauris. Suspendisse potenti. Morbi et felis eros.";
        img.setImageResource(getIntent().getIntExtra("image",0));
        textView.setText(getIntent().getStringExtra("name"));
        priceText.setText(getIntent().getStringExtra("price"));
        notes.setText(note);

    }
}