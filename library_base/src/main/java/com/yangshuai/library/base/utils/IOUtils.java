package com.yangshuai.library.base.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author hcp
 * @Created 7/17/19
 * @Editor hcp
 * @Edited 7/17/19
 * @Type
 * @Layout
 * @Api
 */
public class IOUtils {

    /**
     * 从raw文件读取文本
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getStringFromRaw(Context context, int resId) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(context.getResources().openRawResource(resId));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = bufReader.readLine()) != null) {
            result.append(line);
        }
        inputReader.close();
        bufReader.close();
        return result.toString();

    }

    /**
     * 从assets文件读取文本
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = bufReader.readLine()) != null) {
            result.append(line);
        }
        inputReader.close();
        bufReader.close();
        return result.toString();

    }

    /**
     * @param source
     * @param destination
     */
    public static void copy(File source, File destination) {
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, size);
            }
            fileOutputStream.flush();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
