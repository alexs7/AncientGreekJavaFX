package alrosh7;

import java.io.File;

/**
 * Created by alex on 05/07/15.
 */
public class FileAction {

    public FileAction(){}

    public void createRootDir() {
        String rootDirName = "/home/alex/ancientGreekLocalDatabase";
        File rootDir = new File(rootDirName);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }

    }
}
