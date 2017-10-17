package ifeoluwa.partscribber;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartsCribberAdminMenu extends AppCompatActivity
{
    HashMap<String, List<String>> Admin_Menu;
    List<String> Admin_List;
    ExpandableListView exp_list;
    AdminMenuAdapter adapter;
    ActionBar actionBar;
    String GlobalJSONParseResult;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partscribber_adminmenu);
        actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#01579B'>PartsCribber</font>"));

        exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Admin_Menu = AdminMenuDataProvider.getInfo();
        Admin_List = new ArrayList<String>(Admin_Menu.keySet());

        //Initialization of Array Class Object
        adapter = new AdminMenuAdapter(this, Admin_Menu, Admin_List);
        exp_list.setAdapter(adapter);

        exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id)
            {
                if(Admin_Menu.get(Admin_List.get(groupPosition)).get(childPosition).equals("Add New Student"))
                {
                    Intent intent = new Intent(PartsCribberAdminMenu.this, PartsCribberRegisterStudent.class);
                    startActivity(intent);
                }
                if(Admin_Menu.get(Admin_List.get(groupPosition)).get(childPosition).equals("Add New Admin"))
                {
                    Intent intent = new Intent(PartsCribberAdminMenu.this, PartsCribberRegisterAdmin.class);
                    startActivity(intent);
                }
                if(Admin_Menu.get(Admin_List.get(groupPosition)).get(childPosition).equals("View/Edit My Profile"))
                {
                    Intent intent = new Intent(PartsCribberAdminMenu.this, PartsCribberViewProfile.class);
                    startActivity(intent);
                }
                if(Admin_Menu.get(Admin_List.get(groupPosition)).get(childPosition).equals("Change My Password"))
                {
                    Intent intent = new Intent(PartsCribberAdminMenu.this, PartsCribberChangePassword.class);
                    startActivity(intent);
                }
                if(Admin_Menu.get(Admin_List.get(groupPosition)).get(childPosition).equals("View All Equipment"))
                {
                    Intent intent = new Intent(PartsCribberAdminMenu.this, PartsCribberViewCategory.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to exit the application?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                int id = android.os.Process.myPid();
                android.os.Process.killProcess(id);
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}