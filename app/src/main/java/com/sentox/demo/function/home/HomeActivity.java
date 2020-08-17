package com.sentox.demo.function.home;

import android.os.Bundle;

import com.sentox.demo.R;
import com.sentox.demo.function.base.BaseLinearLayoutManager;
import com.sentox.demo.function.base.activity.BaseActivity;
import com.sentox.demo.function.clock.ClockActivity;
import com.sentox.demo.function.gl.GLTestActivity;
import com.sentox.demo.function.lifecycle.LifecycleTestActivity;
import com.sentox.demo.function.math.MathActivity;
import com.sentox.demo.function.rtl.RightToLeftActivity;
import com.sentox.demo.function.rtl.RtlViewPagerTestActivity;
import com.sentox.demo.function.rxtest.RxTestActivity;
import com.sentox.demo.function.web.WebTestActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 描述：主界面
 * 说明：
 * Created by Sentox
 * Created on 2018/9/12
 */
public class HomeActivity extends BaseActivity {

    private List<HomeBtnBean> mBtnList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initList();
        initView();
    }

    private void initView(){
        mAdapter = new HomeAdapter(this,mBtnList);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_home_list);
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(baseLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initList(){
        HomeBtnBean btnBean1 = new HomeBtnBean();
        btnBean1.setStrTitle("时钟测试界面");
        btnBean1.setNextClass(ClockActivity.class);
        mBtnList.add(btnBean1);

        HomeBtnBean btnBean2 = new HomeBtnBean();
        btnBean2.setStrTitle("GL测试界面");
        btnBean2.setNextClass(GLTestActivity.class);
        mBtnList.add(btnBean2);

        HomeBtnBean btnBean3 = new HomeBtnBean();
        btnBean3.setStrTitle("Math数学测试");
        btnBean3.setNextClass(MathActivity.class);
        mBtnList.add(btnBean3);

        HomeBtnBean btnBean4 = new HomeBtnBean();
        btnBean4.setStrTitle("Web拦截测试");
        btnBean4.setNextClass(WebTestActivity .class);
        mBtnList.add(btnBean4);

        HomeBtnBean btnBean5 = new HomeBtnBean();
        btnBean5.setStrTitle("RxJAVA");
        btnBean5.setNextClass(RxTestActivity.class);
        mBtnList.add(btnBean5);

        HomeBtnBean btnBean6 = new HomeBtnBean();
        btnBean6.setStrTitle("LifeCycle");
        btnBean6.setNextClass(LifecycleTestActivity.class);
        mBtnList.add(btnBean6);

        HomeBtnBean btnBean7 = new HomeBtnBean();
        btnBean7.setStrTitle("RTL");
        btnBean7.setNextClass(RightToLeftActivity.class);
        mBtnList.add(btnBean7);

        HomeBtnBean btnBean8 = new HomeBtnBean();
        btnBean8.setStrTitle("ViewPager");
        btnBean8.setNextClass(RtlViewPagerTestActivity.class);
        mBtnList.add(btnBean8);
    }
}
