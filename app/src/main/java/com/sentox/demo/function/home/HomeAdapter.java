package com.sentox.demo.function.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sentox.demo.R;

import java.util.List;

/**
 * 描述：主界面列表Adapter
 * 说明：
 * Created by Sentox
 * Created on 2018/9/12
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder>{

    private Context mContext;
    private List<HomeBtnBean> mList;

    public HomeAdapter(Context context, List<HomeBtnBean> list){
        mList = list;
        mContext = context;
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_home_list, parent, false);
        HomeHolder holder = new HomeHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        final HomeBtnBean bean = mList.get(position);
        holder.mTitle.setText(bean.getStrTitle());
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,bean.getNextClass());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {

        TextView mTitle;

        public HomeHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.tv_item_home_title);
        }
    }

}
