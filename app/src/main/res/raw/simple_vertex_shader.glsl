attribute vec4 a_Position;

void main()
{
    //顶点坐标
    gl_Position = a_Position;
    //定义点的大小
    gl_PointSize = 10.0;
}