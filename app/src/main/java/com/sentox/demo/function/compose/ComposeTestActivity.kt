package com.sentox.demo.function.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.sentox.demo.R
import com.sentox.demo.function.base.application.GOApplication
import com.sentox.demo.function.base.log.L
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

/**
 * 描述：compose学习界面
 * 说明：
 * Created by Sentox
 * Created on 2026/4/24
 */
class ComposeTestActivity : ComponentActivity() {

    companion object {
        private const val TAG = "ComposeTestActivity"
    }

    private val mViewModel: ComposeTestViewModel by lazy { ViewModelProvider(this).get(ComposeTestViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Preview()
        }
    }

    @Preview(showBackground = true)   //preview只能作用在没有传参的composable注释的方法中
    @Composable
    private fun Preview() {
        TestLayout()
    }

    @Composable
    private fun TestLayout() {

        Column(//三大布局-纵向
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            //使用viewModel会造成无法预览以及无法只运行当前页，所以先注释掉
//            val num by mViewModel.num.collectAsState()
//            val doubleNum by mViewModel.doubleNum.collectAsState()
//            CounterWidget(num, "+1", { mViewModel.clickPlus() })
//            CounterWidget(doubleNum, "+2", { mViewModel.clickDoublePlus() })

            PointerInputEvent()

            Box(//三大布局-box，类似FrameLayout
                modifier = Modifier
                    .background(color = Color(0x80000000))
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(R.mipmap.ic_head),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .align(Alignment.Center)
                        .border(2.dp, color = Color.Red, CircleShape)
                        .rotate(180f)
                )
                Text(text = "test box", fontSize = 20.sp, color = Color.White, modifier = Modifier.align(Alignment.TopEnd))
            }
            Row(//三大布局-横向
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Button(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
                    Toast.makeText(GOApplication.getAppContext(), "按钮被点击", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "测试按钮", color = Color(0xffffffff), fontSize = 16.sp)
                }

                //输入框
                var text by remember { mutableStateOf("") }
                TextField(value = text, onValueChange = {
                    text = it
                }, placeholder = {
                    Text(text = "Enter your name")
                }, colors = TextFieldDefaults.colors(//设置颜色参数
                    focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent
                )
                )

                //转圈进度条
                CircularProgressIndicator(color = Color.Red)
                //长条形进度条
                LinearProgressIndicator(progress = 0.8f, trackColor = Color.Red, color = Color.Yellow)

            }


            //网络加载Image控件
            AsyncImage(
                model = "https://img-blog.csdnimg.cn/20200401094829557.jpg",
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Black)
            )

            //列表，不能与控件同时处于可滑动状态所以注释掉了
            //            Conversation(SampleData.conversationSample)
        }
    }

    @Composable
    private fun CounterWidget(num: Int, btnText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
        Column(modifier = modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            Text("$num", fontSize = 16.sp)
            Button(onClick = {
                onClick()
            }) {
                Text(btnText)
            }
        }
    }

    /**
     *  最简单的点击监听
     * **/
    @Composable
    private fun PointerInputEvent() {
        Box(modifier = Modifier
            .requiredSize(100.dp)
            .background(Color.Blue)
            .pointerInput(Unit) {
                //监听屏幕触摸事件，类似监听onTouch()
                //启动一个协程，运行在ui线程，传入Unit是因为不需要在参数变化时重启这个协程
                awaitPointerEventScope {//挂起函数作用域，等待接收点击事件
                    while (true) {//无限循环保持监听，只执行一次
                        val event = awaitPointerEvent()//暂停，只有真正触发事件后才向下执行
                        L.info(Companion.TAG, "event: ${event.type}")

                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {//监听点击事件，这个作用域也是阻塞性的，所以不能更下面的共用
                    L.info(TAG, "Tap=$it")
                }
            }
            .pointerInput(Unit) {//监听滑动事件
                detectDragGestures { change, dragAmount ->
                    L.info(TAG, "Dragging")
                }
            }
            .clickable {
                L.info(TAG, "监听点击事件")
                Toast
                    .makeText(GOApplication.getAppContext(), "测试点击", Toast.LENGTH_SHORT)
                    .show()
            }) {
            var offsetX by remember { mutableStateOf(0f) }//remember函数的作用是在控件重组时包裹的变量得到保留
            var offsetY by rememberSaveable { mutableStateOf(0f) }//rememberSaveable在横竖屏切换时数值会被保留
            Image(painter = painterResource(R.mipmap.ic_head), contentDescription = "", modifier = Modifier
                .requiredSize(30.dp)
//                    .clip(CircleShape)//在offset前调用会让圆形切割效果无法随着拖动手势移动
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .clip(CircleShape)//顺序非常有讲究，因为Modifier在Composable渲染的时候是顺序执行的
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()//消耗点击事件
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                })
        }
    }

    @Composable
    private fun Conversation(list: List<Message>) {
        //纵向列表
        LazyColumn {
            items(list) { message ->
                MessageCard(message)
            }
        }
    }


    @Composable
    private fun MessageCard(message: Message) {

        Row(modifier = Modifier.padding(all = 8.dp)) {

            val bitmap: ImageBitmap = ImageBitmap.imageResource(R.mipmap.ic_head)
            Image(
//                painter = painterResource(R.mipmap.ic_head), "desc",
                bitmap = bitmap,
                contentDescription = "this is my avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable {

                    })

            Spacer(modifier = Modifier.width(8.dp))

            //是否展开的状态值
            var isExpanded by remember { mutableStateOf(false) }

            //根据是否展开而改变的色值,preview无法看到改变，需要运行
            val surfaceColor by animateColorAsState(
                targetValue = if (isExpanded) Color(0xFFCCCCCC) else Color(0xFFFF0000)
            )

            Column() {
                Text(
                    text = message.author,
                    color = Color(0xffff0000),
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium,
                    shadowElevation = 5.dp,
                    color = surfaceColor,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }

                ) {

                    Text(
                        text = message.body,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(5.dp)

                    )
                }
            }
        }


    }

    data class Message(val author: String, val body: String)

    class ComposeTestViewModel : ViewModel() {
        private val _num = MutableStateFlow(0)
        private val _doubleNum = MutableStateFlow(0)

        val num = _num.asStateFlow()
        val doubleNum = _doubleNum.asStateFlow()

        fun clickPlus() {
            _num.value += 1
        }

        fun clickDoublePlus() {
            _doubleNum.value += 2
        }
    }
}