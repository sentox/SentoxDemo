precision mediump float;

//二维纹理变量数组
uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}