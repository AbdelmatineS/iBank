package tn.project.mybank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username =findViewById(R.id.username);
        EditText password =findViewById(R.id.password);
        TextView signup = findViewById(R.id.signup);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "LOGIN !!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                startActivity(intent);
            }
        });

    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });

    }
}