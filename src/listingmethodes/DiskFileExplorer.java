/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listingmethodes;

/**
 *
 * @author meisslj1
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Lister le contenu d'un répertoire
 * http://www.fobec.com/java/964/lister-fichiers-dossiers-repertoire.html
 *
 * @author fobec 2010
 */
public class DiskFileExplorer {
    
    private String initialpath = "";
    private Boolean recursivePath = false;
    public int filecount = 0;
    public int dircount = 0;
    ArrayList listefichiers;
    ArrayList listedossiers;
    ListingMethodes driver;
 
    JSONObject drivername;
    
    File chemin;
    FileWriter cheminwriter;

    /**
     * Constructeur
     *
     * @param path chemin du répertoire
     * @param subFolder analyse des sous dossiers
     */
    public DiskFileExplorer(String path, Boolean subFolder, ListingMethodes l, FileWriter f) {
        super();
        this.initialpath = path;
        this.recursivePath = subFolder;
        listedossiers = new ArrayList();
        listefichiers = new ArrayList();
        driver = l;
      
        
        drivername = new JSONObject();
        cheminwriter = f;
        
    }
    
    public void list() throws IOException, FileNotFoundException, JSONException {
        drivername = this.listDirectory(this.initialpath);
        this.writefile();
    }
    
    private JSONObject listDirectory(String dir) throws FileNotFoundException, IOException, JSONException {
        File file = new File(dir);
        File[] files = file.listFiles();
        
        JSONObject nom = new JSONObject();
        String nomdriver = "générique";
        
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {
                    JSONObject temp = new JSONObject();
                    System.out.println("Dossier: " + files[i].getAbsolutePath());
                    nomdriver = files[i].getName();
                    temp = this.listFiles(files[i].getAbsolutePath());
                    nom.put(""+nomdriver+"", temp);
                    this.dircount++;
                }

                /*       if (files[i].isDirectory() == true && this.recursivePath == true) {
                    this.listDirectory(files[i].getAbsolutePath());
                }*/
            }
            
            this.dircount++;
            
        }
        
        return nom;
    }
    
    private JSONObject listFiles(String dir) throws FileNotFoundException, IOException, JSONException {
        File file = new File(dir);
        File[] files = file.listFiles();
        String[] liste = file.list();
        JSONObject drivertypeDelphi = new JSONObject();
        JSONObject drivertypeCplus = new JSONObject();
        JSONObject drivetype = new JSONObject();
        for (int j = 0; j < liste.length; j++) {
            if (liste[j].endsWith(".pas") == true) {
                System.out.println("  Fichier: " + files[j].getName());
                File f = new File(files[j].getPath());
                BufferedReader reader = new BufferedReader(new FileReader(f));
                drivertypeDelphi.put(""+files[j].getName()+"",driver.readDelphi(reader));
                drivetype.put("Delphi", drivertypeDelphi);
            }
            if (liste[j].endsWith(".h") == true) {
                System.out.println("  Fichier: " + files[j].getName());
                File f = new File(files[j].getPath());
                BufferedReader reader = new BufferedReader(new FileReader(f));
                drivertypeCplus.put(""+files[j].getName()+"",driver.readCPLUS(reader));
                drivetype.put("CPlus", drivertypeCplus);
            }
        }
      
        this.filecount++;
        
        return drivetype;
    }
    
    
    void writefile() throws IOException {
        
        drivername.writeJSONString(cheminwriter);
        cheminwriter.close();
    }
}
