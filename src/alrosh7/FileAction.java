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

    public FileAction(){
        String homeDir = System.getProperty("user.home");
        String seperator = System.getProperty("file.separator");
        rootDirName = homeDir + seperator + "ancientGreekLocalDatabase";

        File rootDir = new File(rootDirName);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }
    }

    public String getRootDirName() {
        return rootDirName;
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

    public boolean deleteFile(String uniqueID){
        File fileToDelete = new File(rootDirName + "/" + uniqueID + ".json");
        if(fileToDelete.exists()) {
            fileToDelete.delete();
            return true;
        }else{
            return false;
        }
    }
}
