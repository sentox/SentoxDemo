package com.sentox.demo.utils.opengl;

import com.sentox.demo.function.base.log.Loger;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * 描述：着色器帮助类
 * 说明：
 * Created by Sentox
 * Created on 2018/11/2
 */
public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    /**
     * 编译顶点着色器
     **/
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * 编译片段着色器
     **/
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            Loger.i(TAG, "不能创建新的着色器");
            return 0;
        }
        //将着色器代码上传到着色器对象中
        glShaderSource(shaderObjectId, shaderCode);
        //编译这个着色器
        glCompileShader(shaderObjectId);
        //检查编译状态，状态写入compileStatus的第0个元素
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        Loger.i(TAG, "编译结果：" + "\n" + shaderCode + "\n" + glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] == 0) {
            Loger.i(TAG, "编译失败，删除着色器对象");
            glDeleteShader(shaderObjectId);
            return 0;
        }
        return shaderObjectId;
    }

    /**
     * 新建程序对象并附上着色器
     **/
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //新建OpenGL 程序对象
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Loger.i(TAG, "无法创建新的OpenGL的program");
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        Loger.i(TAG, "链接program返回结果：\n" + glGetProgramInfoLog(programObjectId));
        if (linkStatus[0] == 0) {
            //如果链接失败，删除程序对象
            glDeleteProgram(programObjectId);
            Loger.i(TAG, "链接program失败");
            return 0;
        }
        return programObjectId;
    }

    /**
     * 验证program是否有效
     **/
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Loger.i(TAG, "Program验证结果：" + validateStatus[0] + "\n Log:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource,String fragmentShaderSource){
        int program;

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        program = linkProgram(vertexShader,fragmentShader);

       validateProgram(program);

       return program;
    }
}
