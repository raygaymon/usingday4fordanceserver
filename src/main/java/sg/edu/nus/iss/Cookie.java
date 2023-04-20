package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Cookie {

    List<String> cookieInspos = null;

    // function input is the cookie file with all the cookies | FileNotFoundException because we dealing with files...?
    public void cookieRead(String fileName) throws IOException, FileNotFoundException {

        // instantiate cookie collection
        cookieInspos = new ArrayList<>(); //correct

        // get a file object to pass the fileName
        File cookieSource = new File(fileName); //correct

        // use FileReader and BufferedReader for reading the cookie file
        FileReader fr = new FileReader(cookieSource); //originlly was fileName, read the file that was just instantiated in step above
        BufferedReader br = new BufferedReader(fr); //correct

        // while loop to loop through the file
        String reading = ""; //correct
        //int i = 0;
        while ((reading = br.readLine()) != null) {

            // read each cookie line and add into collection object
            //System.out.println(i + "cookie(s) loaded");
            cookieInspos.add(reading); //correct
            //i++;
            //no need to have extra br.readline it's already running in for-loop
        }

        System.out.println("Cookies loaded and ready to crumble");

        // close the readers
        br.close();
        fr.close();
    }

    public String getRandomCookie() {

        //math random function to get a random item from cookie object
        
        Random random = new Random(); // random must be defined first before using

        //must make sure cookies are loaded in cookie colection first
        if (cookieInspos == null) {
            System.out.println("Your cookie tray is empty, you have no luck today");
        } 

        //if cookies are loaded, then send a random one
        String drawnCookie = cookieInspos.get(random.nextInt(cookieInspos.size())); // use random with .nextInt to get number
        return drawnCookie;
    }
}
