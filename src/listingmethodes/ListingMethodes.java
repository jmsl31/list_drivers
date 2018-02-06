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

    }

    JSONObject readDelphi(BufferedReader buff) throws IOException, JSONException {
        String ligne, nommethode = null;
        int compt = 0;
        JSONObject nom = new JSONObject();
        while ((ligne = buff.readLine()) != null) {

            if (ligne.contains("unit")) {
                nommethode = ligne.substring(5, ligne.length() - 1);
            }

            if (ligne.contains("procedure")) {
                compt++;
                String s = ligne;
                nom.put("methodes" + compt, s);

            }

        }

        return (nom);
    }

    JSONObject readCPLUS(BufferedReader buff) throws IOException, JSONException {
        String ligne, nommethode = null;
        int compt = 0;
        int i =0;
        JSONObject nom = new JSONObject();
        while ((ligne = buff.readLine()) != null) {
            i++;   
            if (!ligne.isEmpty()) {

                if (ligne.contains("class")) {
                    if (ligne.contains("/*") || ligne.contains("* \\class")|| ligne.contains("_class")|| ligne.contains("* class")
                       || ligne.contains("! \\class")|| ligne.contains("* \\brief class")|| ligne.contains("classe")|| ligne.contains("The class")|| ligne.contains("Ivi_Class"))
                    {
                    }
                    else {
                        try {
                            nommethode = ligne.substring(5, ligne.indexOf(":"));
                        } catch (Exception e) {
                            try {
                                nommethode = ligne.substring(5, ligne.indexOf("{"));
                            } catch (Exception ex) {
                                 System.out.println(i);
                            }
                            
                           
                        }
                        
                    }
                }

                if (ligne.contains("virtual ")) {
                    compt++;
                    String s = ligne;
                    nom.put("methodes" + compt, s);

                }
            }
        }
        return (nom);
    }

}
