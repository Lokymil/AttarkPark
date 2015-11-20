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
        this.addPark(new Park((long) 0,"Parc Asterix","Parc sur l'univers de la célèbre BD du même nom","http://www.parcasterix.fr/", "France", new LatLng(49.1344,2.5703),"http://www.parcasterix.fr/sites/default/themes/asterix/logo.png"));
        this.addPark(new Park((long) 1,"DisneyLand Paris","Le parc le plus connu de Paris sur l'univers de disney.","http://www.disneylandparis.fr/","France" , new LatLng(48.8687,2.7818), "https://cdn.thewaltdisneycompany.com/sites/default/files/styles/242x136_scale_crop/public/OurBusinesses_GroupView_Parks_DLP_logo.png?itok=tIxHC1rH"));
        this.addPark(new Park((long) 2,"Europa-Park","Le plus grand parc a thème d'Allemagne et le deuxième plus populaire d'Europe.","http://www.europapark.de/fr","Allemagne" , new LatLng(48.2683,7.7208), "https://upload.wikimedia.org/wikipedia/en/4/45/EuropaPark_logo.jpg"));
        this.addPark(new Park((long) 3,"Port Aventura","Grand parc à thème d'Espagne.","http://fr.portaventura.com/","Espagne" , new LatLng(41.0847,1.1514), "https://upload.wikimedia.org/wikipedia/en/thumb/f/f8/PortAventura_Logo.svg/1280px-PortAventura_Logo.svg.png"));
        this.addPark(new Park((long) 4,"Futuroscope","Parc sur le thème de la technologie et de son évolution.","http://www.futuroscope.com/","France" , new LatLng(46.6692,0.3689), "https://lh5.googleusercontent.com/-BkJfKjR3Sw0/AAAAAAAAAAI/AAAAAAAAAAA/wEKQaNcuwdk/s0-c-k-no-ns/photo.jpg"));
    }

}
