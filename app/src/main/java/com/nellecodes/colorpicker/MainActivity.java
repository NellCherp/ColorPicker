package com.nellecodes.colorpicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Open for display https://erratic-melon-promotion.glitch.me/

    RelativeLayout red_layout, yellow_layout, blue_layout, green_layout, orange_layout, purple_layout, register;
    GridLayout grid;
    ColorManager colorManager;
    EditText participantIdTxt;
    Button startBtn;
    Participant user;
    String userid;
    Integer count = 0;
    Integer nrPicks = 5;
    long startTime, endTime;

    private DatabaseReference mDatabase;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        red_layout = findViewById(R.id.red_choise);
        yellow_layout = findViewById(R.id.yellow_choise);
        blue_layout = findViewById(R.id.blue_choise);
        green_layout = findViewById(R.id.green_choise);
        purple_layout = findViewById(R.id.purple_choise);
        orange_layout = findViewById(R.id.orange_choise);
        grid = findViewById(R.id.grid);
        colorManager = new ColorManager();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        startBtn = findViewById(R.id.startBtn);
        participantIdTxt = findViewById(R.id.participant_id);
        register = findViewById(R.id.register_layout);

        red_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.RED);
            }
        });
        blue_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.BLUE);
            }
        });

        yellow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.YELLOW);
            }
        });
        green_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.GREEN);

            }
        });
        orange_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.ORANGE);

            }
        });
        purple_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChosen(Color.PURPLE);

            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClicked();
            }
        });

        mDatabase.child("current").child("text").setValue("NONE");
        mDatabase.child("current").child("color").setValue("NONE");
        grid.setClickable(false);
    }

    private void startClicked() {
       String id = participantIdTxt.getText().toString();
       if (!id.isEmpty()) {
           participantIdTxt.setText("");
           register.setVisibility(View.GONE);
           grid.setClickable(true);
           user = new Participant(id);
           userid = id;
           colorManager.setRandomColor();
           mDatabase.child("current").child("color").setValue(colorManager.getColor().toString());
           mDatabase.child("current").child("text").setValue(colorManager.getColorTxt().toString());
           startTime = System.currentTimeMillis();
       }
    }
    private void colorChosen(Color color) {
        count += 1;
        boolean correct;
        if (color == colorManager.color) {
            correct = true;
        } else {
            correct = false;
        }
        String correctStr = String.valueOf(correct);
        Choice choice = new Choice(colorManager.getColor().toString(), color.toString(), colorManager.getColorTxt().toString(), getTime(), correctStr);
        startTime = System.currentTimeMillis();
        colorManager.setRandomColor();
        mDatabase.child("current").child("color").setValue(colorManager.getColor().toString());
        mDatabase.child("current").child("text").setValue(colorManager.getColorTxt().toString());
        mDatabase.child("tests").child(userid).child("choices").push().setValue(choice)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Data written successfully");
                    } else {
                        System.out.println("Data write failed: " + task.getException());
                    }
                });

        if (count >= nrPicks) {
            grid.setClickable(false);
            count = 0;
            mDatabase.child("current").child("text").setValue("NONE");
            mDatabase.child("current").child("color").setValue("NONE");
            register.setVisibility(View.VISIBLE);
        }
    }

private String getTime() {
    endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;
    String timeAsString = Long.toString(timeTaken);
    return timeAsString;
}

    @Override
    protected void onDestroy() {
        grid.setClickable(false);
        count = 0;
        mDatabase.child("current").child("text").setValue("NONE");
        mDatabase.child("current").child("color").setValue("NONE");
        register.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}