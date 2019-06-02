package com.web.design.compiler;

import java.io.File;

public class FileUtil {
    public static boolean fileDelete(String filename){
        File file=new File(filename);
        if(file.exists() && file.isFile()){
            return file.delete();
        }else{
            return false;
        }
    }

    public static boolean clearFolder(String directory){
        boolean result =true;
        File dir=new File(directory);
        if(dir.exists() && dir.isDirectory()){
            File[] files=dir.listFiles();
            for(int i=0; i<files.length; i++){
                result=files[i].delete()&&result;
            }
        }else{
            return false;
        }

        return result;
    }
}
