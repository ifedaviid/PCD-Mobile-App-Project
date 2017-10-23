package ifeoluwa.partscribber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;


public class PartsCribberSelectCategory extends AppCompatActivity
{
    String jsonstring;
    JSONObject jsonObject;
    JSONArray jsonArray;
    SelectCategoryAdapter selectCategoryAdapter;
    ListView listView;
    ActionBar actionBar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partscribber_selectcategory);
        actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#01579B'>PartsCribber</font>"));

        new CategoryInfoBackgroundTasks(this).execute();

        listView = (ListView) findViewById(R.id.listview);
        selectCategoryAdapter = new SelectCategoryAdapter(this, R.layout.selectcategory_rowlayout);
        listView.setAdapter(selectCategoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                intent = new Intent(PartsCribberSelectCategory.this, PartsCribberSelectTool.class);
                intent.putExtra("selectedCategory", selectedCategory);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onRestart()
    {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,0);
    }*/

    @Override
    protected void onRestart()
    {
        super.onRestart();
        recreate();
    }

    class CategoryInfoBackgroundTasks extends AsyncTask<Void, Void, String>
    {
        String json_url;
        String JSON_STRING;
        Context ctx;
        AlertDialog.Builder builder;
        private Activity activity;
        private AlertDialog loginDialog;

        public CategoryInfoBackgroundTasks(Context ctx)
        {
            this.ctx = ctx;
            activity = (Activity)ctx;
        }

        @Override
        protected void onPreExecute()
        {
            builder = new AlertDialog.Builder(activity);
            View dialogView = LayoutInflater.from(this.ctx).inflate(R.layout.progress_dialog, null);
            ((TextView)dialogView.findViewById(R.id.tv_progress_dialog)).setText("Fetching Categorized Data");
            loginDialog = builder.setView(dialogView).setCancelable(false).setTitle("Please Wait").show();
            json_url = "http://partscribdatabase.tech/androidconnect/fetchCategoryData.php";
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
            loginDialog.dismiss();
            jsonstring = result;
            HashSet<String> arraylistofCategoryObjects = new HashSet<String>();

            try
            {
                jsonObject = new JSONObject(jsonstring);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;

                String itemCategory;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    itemCategory = JO.getString("category");
                    arraylistofCategoryObjects.add(itemCategory.toUpperCase());
                    count++;
                }
                for (String item : arraylistofCategoryObjects)
                {
                    selectCategoryAdapter.add(item);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
