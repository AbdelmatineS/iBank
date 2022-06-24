package tn.project.mybank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListUser extends AppCompatActivity {

    ListView ls;
    ProgressDialog dialog;
    JSONParser parser= new JSONParser();
    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String,String>>();
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        ls = findViewById(R.id.lst);

        new All().execute();

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.id_item);

                Intent intent = new Intent(ListUser.this, DetailActivity.class);
                intent.putExtra("id",t.getText().toString());
                startActivity(intent);
            }
        });
    }

    class All extends AsyncTask<String, String, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListUser.this);
            dialog.setMessage("Chargement du data en cours");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map = new HashMap<>();
            JSONObject object= parser.makeHttpRequest("http://192.168.1.27/user/all.php","GET",map);

            try {
                success = object.getInt("success");

                if(success==1){

                    JSONArray users = object.getJSONArray("users");

                    for(int i = 0; i<users.length();i++){

                        JSONObject user= users.getJSONObject(i);

                        HashMap<String, String> m = new HashMap<String, String>();

                        m.put("id",user.getString("id"));
                        m.put("nom",user.getString("nom"));
                        m.put("prenom",user.getString("prenom"));
                        m.put("password",user.getString("password"));
                        m.put("image",user.getString("image"));
                        m.put("username",user.getString("username"));
                        m.put("roles",user.getString("roles"));

                        values.add(m);

                    }

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

            SimpleAdapter adapter = new SimpleAdapter(ListUser.this,values,R.layout.item,
                    new String[]{"id","nom","roles"}, new int[]{R.id.id_item, R.id.name_item,R.id.role_item});

            ls.setAdapter(adapter);

        }
    }
}