
//mat4代表是4x4的矩阵
uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_Color;

void main()
{
    //顶点坐标
    gl_Position = u_Matrix * a_Position;
    //Varying着色数值，相当于渐进色的起始点或者结束点数值
    v_Color = a_Color;
    //定义点的大小
    gl_PointSize = 10.0;
}