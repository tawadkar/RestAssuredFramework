package resources;

import pojoClasses.Location;
import pojoClasses.addLocation;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuilder {

    public addLocation addPlacePayload(){
        pojoClasses.addLocation l = new addLocation();
        l.setAccuracy(50);
        l.setAddress("29 side layout cohen 09");
        l.setLanguage("French-IN");
        l.setPhone_number("(+91) 983 893 3937");
        l.setWebsite("https://rahulshettyacademy.com");
        l.setName("Tanmay Frontline house");

        //Set type has return type as List, therefore we created list before passing it to setTypes
        //types is a separate list of strings and do not contain key value pairs hence pojo is not created
        //  key=getter value=setter
        List<String> typeList = new ArrayList<>();
        typeList.add("shoe park");
        typeList.add("shop");
        l.setTypes(typeList);

        /*
         * Location is separate array having key value pairs therefore we created separate pojo class
         */
        Location lo = new Location();
        lo.setLat(-38.383494);
        lo.setLng(33.427362);
        l.setLocation(lo);

        return l;
    }
}
