package tn.project.mybank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity {


    EditText et_nom_add,et_prenom_add,et_pass_add,et_image_add,et_username_add,et_role_add;
    Button btn_user_add;
    String nom,prenom,password,image,username,role;

    ProgressDialog dialog;
    JSONParser parser= new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_nom_add = findViewById(R.id.et_nom_add);
        et_prenom_add = findViewById(R.id.et_prenom_add);
        et_pass_add = findViewById(R.id.et_pass_add);
        et_image_add = findViewById(R.id.et_image_add);
        et_username_add = findViewById(R.id.et_username_add);
        et_role_add = findViewById(R.id.et_role_add);

        btn_user_add = findViewById(R.id.btn_user_add);


        btn_user_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Add().execute();

            }
        });

    }

    class Add extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AddActivity.this);
            dialog.setMessage("Ajout en cours!");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            nom = et_nom_add.getText().toString().trim();
            prenom = et_prenom_add.getText().toString().trim();
            password = et_pass_add.getText().toString().trim();
            image = et_image_add.getText().toString().trim();
            username = et_username_add.getText().toString().trim();
            role = et_role_add.getText().toString().trim();

            HashMap<String,String> map= new HashMap<String,String>();
            map.put("nom", nom);
            map.put("prenom", prenom);
            map.put("password", password);
            map.put("image", image);
            map.put("username", username);
            map.put("roles", role);

            JSONObject object = parser.makeHttpRequest("http://192.168.1.27/user/add.php","GET",map);


            try {
                success= object.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();

            if(success == 1){
                Toast.makeText(AddActivity.this,"Ajout effectué avec succes",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddActivity.this, AdminDashboard.class);
                startActivity(it);
            }else{
                Toast.makeText(AddActivity.this,"Echec de l'ajout",Toast.LENGTH_SHORT).show();
            }
        }
    }

}