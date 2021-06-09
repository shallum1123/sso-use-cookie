package com.sso.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author zyc
 * @Date 2021/1/13 19:42
 * @Version V1.0
 */

public class Atest {

    public static String[] getFileName(String path) {
        File file = new File(path);
        String[] fileName = file.list();
        for (String name : fileName) {
            String substring = name.substring(0, name.lastIndexOf("_"));
            System.out.println(substring);

        }
        return fileName;

    }

    public static void getAllFileName(String path, ArrayList<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null){
            fileName.addAll(Arrays.asList(names));
        }
        for (File f : files) {
            if (f.isDirectory()) {
                getAllFileName(f.getAbsolutePath(), fileName);
            }
        }
    }

    public static void main(String[] args) {
        String path = "F:\\JavaWorkSpace\\新建文件夹\\files";
        String[] fileName = getFileName(path);//双斜杠其中一个用来转义
        for (String name : fileName) {
            System.out.println(name);
        }

        ArrayList<String> listFileName = new ArrayList<String>();
        getAllFileName(path, listFileName);
        for (String name : listFileName) {
            System.out.println(name);

        }


    }

}
