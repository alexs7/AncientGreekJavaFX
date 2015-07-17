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
        rootDirName = "/home/alex/ancientGreekLocalDatabase";
    }

    public String getRootDirName() {
        return rootDirName;
    }

    public void createRootDir() {
        File rootDir = new File(rootDirName);
        if(!rootDir.exists()){
            rootDir.mkdirs();
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
