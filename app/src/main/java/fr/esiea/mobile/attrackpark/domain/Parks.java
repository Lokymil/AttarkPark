package fr.esiea.mobile.attrackpark.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christophe on 12/11/2015.
 */
public class Parks {

    private static Parks instance;

    public static Parks getInstance(){
        if (instance == null){
            instance = new Parks();
        }

        return instance;
    }

    Map<Long, Park> list = new HashMap<Long, Park>();

    private Parks(){
        init();
    }

    public void addPark(Park park){
        list.put(park.getId(), park);
    }

    public ArrayList<Park> getParks(){
        return new ArrayList(list.values());
    }

    public Park getParkById(Long id){
        return list.get(id);
    }

    public void init(){
        this.addPark(new Park((long) 0,"park1","descrip1","www.google.fr"));
        this.addPark(new Park((long) 1,"park2","descrip2","www.google.fr"));
        this.addPark(new Park((long) 2,"park3","descrip3","www.google.fr"));
        this.addPark(new Park((long) 3,"park4","descrip4","www.google.fr"));
        this.addPark(new Park((long) 4,"park5","descrip5","www.google.fr"));
    }

}
