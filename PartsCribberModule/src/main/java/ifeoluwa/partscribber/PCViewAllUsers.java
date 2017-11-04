package ifeoluwa.partscribber;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class PCViewAllUsers extends Fragment
{
    View finder;
    String jsonstring;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayAdapter<String> adapter;
    ListView listView;
    SearchView searchView;
    ActionBar actionBar;
    Intent intent;
    int count;
    android.support.v7.app.AlertDialog dialog;

    public PCViewAllUsers()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        finder = inflater.inflate(R.layout.pcviewallusers_fragment, container, false);
        new fetchAllStudentsBackgroundTasks(getActivity()).execute();
        return finder;
    }

    class fetchAllStudentsBackgroundTasks extends AsyncTask<Void, Void, String>
    {
        String json_url;
        String JSON_STRING;
        Context ctx;
        AlertDialog.Builder builder;
        private Activity activity;
        private AlertDialog loginDialog;
        List<String> arraylistofStudentObjects = new ArrayList<String>();
        List<String> listItems = new ArrayList<String>();
        String[] items;

        public fetchAllStudentsBackgroundTasks(Context ctx)
        {
            this.ctx = ctx;
            activity = (Activity)ctx;
        }

        @Override
        protected void onPreExecute()
        {
            builder = new AlertDialog.Builder(activity);
            View dialogView = LayoutInflater.from(this.ctx).inflate(R.layout.progress_dialog, null);
            ((TextView)dialogView.findViewById(R.id.tv_progress_dialog)).setText("Fetching Server Data");
            loginDialog = builder.setView(dialogView).setCancelable(false).setTitle("Please Wait").show();
            json_url = "http://partscribdatabase.tech/androidconnect/fetchAllStudents.php";
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
            count = 0;

            listView =(ListView) finder.findViewById(R.id.listview);
            searchView = (SearchView) finder.findViewById(R.id.searchView);
            searchView.setIconified(false);
            searchView.clearFocus();
            initList();

            try
            {
                jsonObject = new JSONObject(jsonstring);
                jsonArray = jsonObject.getJSONArray("server_response");

                String username;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    username = JO.getString("username");
                    arraylistofStudentObjects.add(username);
                    adapter.add(username.toUpperCase());
                    count++;
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        //if we had ParentActivity then we can do
                        String selectedID = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(getBaseContext(), selectedID ,Toast.LENGTH_LONG).show();
                        intent = new Intent(ctx, PartsCribberStudentInfo.class);
                        intent.putExtra("selectedID", selectedID);
                        startActivity(intent);
                    }
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
                {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });

                searchView.setOnSearchClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        searchView.onActionViewExpanded();
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        public void initList()
        {
            items = new String[arraylistofStudentObjects.size()];
            for(int i=0; i < arraylistofStudentObjects.size(); i++)
            {
                items[i] = arraylistofStudentObjects.get(i);
            }
            listItems=new ArrayList<>(Arrays.asList(items));
            adapter=new ArrayAdapter<String>(ctx, R.layout.viewalltools_rowlayout,R.id.viewalltools_itemnametext, listItems);
            listView.setAdapter(adapter);
        }
    }
}
