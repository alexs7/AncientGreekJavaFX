package alrosh7;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by alex on 05/07/15.
 */
public class FileAction {

    private String rootDirName;
    private String objectsFileName;

    public FileAction(){
        rootDirName = "/home/alex/ancientGreekLocalDatabase";
        objectsFileName = "/home/alex/ancientGreekLocalDatabase/phrases.json";
    }

    public void createRootDir() {
        File rootDir = new File(rootDirName);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }
    }

    public JSONObject loadJSONFile(){
        JSONParser parser = new JSONParser();
        File JSONFile = new File(objectsFileName);
        if(JSONFile.exists()){
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(objectsFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        }else{
            try {
                JSONFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}