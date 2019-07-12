
//mat4代表是4x4的矩阵
uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;

void main()
{
    //顶点坐标
    gl_Position = u_Matrix * a_Position;
    v_TextureCoordinates = a_TextureCoordinates;
}