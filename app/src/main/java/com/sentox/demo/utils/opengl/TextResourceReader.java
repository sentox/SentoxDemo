package com.sentox.demo.utils.opengl;

import android.content.res.Resources;

import com.sentox.demo.function.base.application.GOApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 描述：着色器读取类
 * 说明：
 * Created by Sentox
 * Created on 2018/11/2
 */
public class TextResourceReader {

    public static String readTextFileFromResource(int resourceId){
        StringBuilder body = new StringBuilder();
        try{
            InputStream inputStream =
                    GOApplication.getAppContext().getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while((nextLine = bufferedReader.readLine())!=null){
                body.append(nextLine);
                body.append('\n');
            }
        }catch (IOException e){
            throw new RuntimeException("Could not open resource:"+resourceId,e);
        }catch (Resources.NotFoundException nfe){
            throw new RuntimeException("Resource not found:"+resourceId,nfe);
        }
        return body.toString();
    }

}
