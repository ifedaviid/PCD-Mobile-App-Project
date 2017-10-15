package ifeoluwa.partscribber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ifeoluwa David on 2017-10-09.
 */

public class AdminMenuDataProvider
{
    public static HashMap<String, List<String>> getInfo()
    {
        HashMap <String, List<String>> Admin_Menu = new HashMap <String, List<String>>();

        List<String> add_new_user = new ArrayList<String>();
        add_new_user.add("Add New Student");
        add_new_user.add("Add New Admin");

        List<String> rentals_and_returns = new ArrayList<String>();
        rentals_and_returns.add("Rent Equipment");
        rentals_and_returns.add("Return Equipment");

        List<String> inventory = new ArrayList<String>();
        inventory.add("View All Equipment");
        inventory.add("View Rented Equipment");
        inventory.add("Update Inventory Record");

        List<String> profile_settings = new ArrayList<String>();
        profile_settings.add("View/Edit My Profile");
        profile_settings.add("Change My Password");

        Admin_Menu.put("ADD NEW USER", add_new_user);
        Admin_Menu.put("RENTALS & RETURNS", rentals_and_returns);
        Admin_Menu.put("INVENTORY", inventory);
        Admin_Menu.put("PROFILE SETTINGS", profile_settings);

        return Admin_Menu;
    }
}