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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    String id;
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();

    int success, success_mod, success_del;

    String nom,prenom,password,image,username,role;
    EditText et_nom_fetch,et_prenom_fetch,et_password_fetch,et_image_fetch,et_username_fetch,et_role_fetch;
    Button btn_mod, btn_del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();

        et_nom_fetch = findViewById(R.id.et_nom_fetch);
        et_prenom_fetch = findViewById(R.id.et_prenom_fetch);
        et_password_fetch = findViewById(R.id.et_pass_fetch);
        et_image_fetch = findViewById(R.id.et_image_fetch);
        et_username_fetch = findViewById(R.id.et_username_fetch);
        et_role_fetch = findViewById(R.id.et_role_fetch);

        btn_mod = findViewById(R.id.btn_user_mod);
        btn_del = findViewById(R.id.btn_user_delete);

        if(extras!=null){
            id=extras.getString("id");

            new Select().execute();
        }


        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Modifier().execute();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Supprimer().execute();
            }
        });

    }

    class Select extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DetailActivity.this);
            dialog.setMessage("Chargement des données de l'utilisateur");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);

            JSONObject object = parser.makeHttpRequest("http://192.168.1.27/user/one.php","GET",map);

            try {
                success = object.getInt("success");
                if(success==1){

                    JSONArray user = object.getJSONArray("user");
                    JSONObject o = user.getJSONObject(0);
                    //id = o.getString("id");
                    nom = o.getString("nom");
                    prenom = o.getString("prenom");
                    password = o.getString("password");
                    image = o.getString("image");
                    username = o.getString("username");
                    role = o.getString("roles");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();

            et_nom_fetch.setText(nom);
            et_prenom_fetch.setText(prenom);
            et_password_fetch.setText(password);
            et_image_fetch.setText(image);
            et_username_fetch.setText(username);
            et_role_fetch.setText(role);
        }
    }



    class Modifier extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(DetailActivity.this);
            dialog.setMessage("Modification en cours!");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("id",id);

            map1.put("nom", et_nom_fetch.getText().toString());
            map1.put("prenom", et_prenom_fetch.getText().toString());
            map1.put("password", et_password_fetch.getText().toString());
            map1.put("image", et_image_fetch.getText().toString());
            map1.put("username", et_username_fetch.getText().toString());
            map1.put("roles", et_role_fetch .getText().toString());

            JSONObject ob = parser.makeHttpRequest("http://192.168.1.27/user/update.php","GET",map1);


            try {
                success_mod = ob.getInt("success");

                if(success_mod==1){

                    Intent inten = new Intent(DetailActivity.this, ListUser.class);
                    startActivity(inten);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            if(success_mod==0){
                Toast.makeText(DetailActivity.this,"Erreur de modification",Toast.LENGTH_SHORT).show();
            }

        }


    }



    class Supprimer extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DetailActivity.this);
            dialog.setMessage("Suppression en cours!");
            dialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("id",id);

            JSONObject ob = parser.makeHttpRequest("http://192.168.1.27/user/delete.php","GET",map1);


            try {
                success_del = ob.getInt("success");

                if(success_del==1){

                    Intent inty = new Intent(DetailActivity.this, ListUser.class);
                    startActivity(inty);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            if(success_del==0){
                Toast.makeText(DetailActivity.this,"Erreur de Suppression",Toast.LENGTH_SHORT).show();
            }

        }


    }




}