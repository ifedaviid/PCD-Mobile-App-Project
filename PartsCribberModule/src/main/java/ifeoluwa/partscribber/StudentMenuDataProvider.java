package ifeoluwa.partscribber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ifeoluwa David on 2017-10-09.
 */

public class StudentMenuDataProvider
{
    public static HashMap<String, List<String>> getInfo()
    {
        HashMap <String, List<String>> Student_Menu = new HashMap <String, List<String>>();

        List<String> view_equipments = new ArrayList<String>();
        view_equipments.add("Select Equipment");
        view_equipments.add("View By Category");

        List<String> my_rentals = new ArrayList<String>();
        my_rentals.add("My Current Possessions"); //Straight to View Rental Info
        my_rentals.add("My Cart Items"); //Straight to View Cart Info

        List<String> profile_settings = new ArrayList<String>();
        profile_settings.add("View/Edit My Profile");
        profile_settings.add("Change My Password");

        Student_Menu.put("VIEW EQUIPMENTS", view_equipments);
        Student_Menu.put("MY RENTALS", my_rentals);
        Student_Menu.put("PROFILE SETTINGS", profile_settings);

        return Student_Menu;
    }
}
