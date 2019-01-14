package com.sentox.demo.function.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sentox.demo.R;
import com.sentox.demo.function.base.BaseLinearLayoutManager;
import com.sentox.demo.function.clock.ClockActivity;
import com.sentox.demo.function.gl.GLTestActivity;
import com.sentox.demo.function.math.MathActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：主界面
 * 说明：
 * Created by Sentox
 * Created on 2018/9/12
 */
public class HomeActivity extends Activity {

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
    }
}
