package fr.esiea.mobile.attrackpark.domain;

import com.google.android.gms.maps.model.LatLng;

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

    public Park getParkByName(String name){
        ArrayList<Park> list = new ArrayList<Park>(this.list.values());
        Park mPark = null;

        for (Park park : list){
            if (park.getName().equals(name)){
                mPark = park;
                break;
            }
        }

        return mPark;
    }

    public void init(){
        this.addPark(new Park((long) 0,"Parc Asterix","Parc sur l'univers de la célèbre BD du même nom","http://www.parcasterix.fr/", new LatLng(49.1344,2.5703)));
        this.addPark(new Park((long) 1,"park2","descrip2","www.google.fr", new LatLng(0,0)));
        this.addPark(new Park((long) 2,"park3","descrip3","www.google.fr", new LatLng(0,0)));
        this.addPark(new Park((long) 3,"park4","descrip4","www.google.fr", new LatLng(0,0)));
        this.addPark(new Park((long) 4,"park5","descrip5","www.google.fr", new LatLng(0,0)));
    }

}
