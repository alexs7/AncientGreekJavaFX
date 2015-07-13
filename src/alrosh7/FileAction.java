package alrosh7;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by alex on 05/07/15.
 */
public class FileAction {

    private String rootDirName;
    private String objectsFileName;

    public FileAction(){
        rootDirName = "/home/ar1v13/ancientGreekLocalDatabase";
        objectsFileName = rootDirName+"/phrases.json";
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

    public void saveFile(JSONObject saveItem) {
        try {
            FileWriter fileToSave = new FileWriter(rootDirName + "/" + saveItem.get("uniqueID") + ".json");
            fileToSave.write(saveItem.toJSONString());
            fileToSave.flush();
            fileToSave.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
