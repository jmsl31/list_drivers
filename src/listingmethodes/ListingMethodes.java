/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listingmethodes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ListingMethodes {

    JSONObject driver;
    JSONArray methodes;
    String format;

    public enum Type {
        JAVA,
        DELPHI,
        CPLUS,
        PHP;
    };

    public ListingMethodes() throws JSONException {

        driver = new JSONObject();

    }

    JSONObject readDelphi(BufferedReader buff) throws IOException, JSONException {
        String ligne, nommethode = null;
        int compt = 0;
        JSONObject nom = new JSONObject();
        methodes = new JSONArray();
        while ((ligne = buff.readLine()) != null) {
            
            if (ligne.contains("unit")) {
                nommethode = ligne.substring(5, ligne.length()-1);
            }
            
            if (ligne.contains("procedure")) {
                compt++;
                String s = ligne.substring(ligne.lastIndexOf("procedure "), ligne.length() - 1);
                nom.put("methodes"+compt, s);
                
            }

        }
        driver.put(nommethode, nom);
        return (driver);
    }

    JSONObject readCPLUS(BufferedReader buff) throws IOException, JSONException {
        String ligne, nommethode = null;
        int compt = 0;
       
        JSONObject nom = new JSONObject();
        methodes = new JSONArray();
        while ((ligne = buff.readLine()) != null) {
            
            System.out.println("Ligne :" +compt);
            if (!ligne.isEmpty()) {
                
                if (ligne.contains("class")) {
                    if (ligne.contains("/*")) {

                    } else {
                        nommethode = ligne.substring(5, ligne.indexOf(":"));
                    }
                }

                if (ligne.contains("virtual ")) {
                    compt++;
                    String s = ligne.substring(ligne.lastIndexOf("virtual "), ligne.length() - 1);
                    nom.put("methodes"+ compt, s);
                   
                }
            }
        }
        driver.put(nommethode, nom);
        return (driver);
    }

}
