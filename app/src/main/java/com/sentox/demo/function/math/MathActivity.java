package com.sentox.demo.function.math;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sentox.demo.R;
import com.sentox.demo.function.base.activity.BaseActivity;
import com.sentox.demo.function.base.log.Loger;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 描述：验证Activity
 * 说明：
 * Created by Sentox
 * Created on 2018/12/19
 */
public class MathActivity extends BaseActivity {

    private static final String TAG = "MathResult";

    private TextView mTvFunction;
    private TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mTvFunction = findViewById(R.id.tv_math_function);
        mTvResult = findViewById(R.id.tv_math_result);

        mT1.start();
        mT2.start();
        mFlagThreadStart = true;

//        Result result = new Result();
//        mTvFunction.setText("证明：n格放的麦粒总数为2^n-1");
//        mTvResult.setText("证明命题为：" + prove(63, result) + ",\n总数为：" + result.mTotalNumber);
//        //=====================计算乘积组合=======================//
//        int testNumber = 20;
//        mults = new int[testNumber - 2];
//        for (int i = 0; i < testNumber - 2; i++) {
//            mults[i] = i + 2;
//        }
//        Loger.i(TAG, "=====================计算乘积组合=======================");
//        getCombinationOfNumberTimes(testNumber, new ArrayList<Integer>());
//        //=====================归并排序====================================//
//        int[] to_sort = {3434, 3356, 67, 12334, 878667, 387};
//        int[] sortResult = mergeSort(to_sort);
//        String string = "";
//        for (int i = 0; i < sortResult.length; i++) {
//            string = string + "," + sortResult[i];
//        }
//        Loger.i(TAG, "=====================归并排序===============");
//        Loger.i(TAG, string);
//
//        //=====================计算在n个元素的数组中取m个元素的组合数====================
//        Loger.i(TAG, "=====================计算在n个元素的数组中取m个元素的组合数====================");
//        countCombine();
    }

    //===============================线程临时加入验证=====================================//
    boolean mFlagThreadStart = false;

    Thread mT1 = new Thread(new Runnable() {
        @Override
        public void run() {
            Loger.i(TAG, "T1:线程启动");
            boolean flag = true;
            int index = 0;
            try {
            while (flag){
                index++;
                Loger.i(TAG, "T1:"+ index);
                Thread.sleep(1000);
                if(mFlagThreadStart){
                    mT2.join();
                    flag = false;
                }
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Loger.i(TAG, "T1:线程结束");
        }
    });

    Thread mT2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Loger.i(TAG, "T2:线程启动");
                Thread.sleep(4000);
                Loger.i(TAG, "T2：线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    //======================归并排序=============================================

    /**
     * 归并排序算法(从小到大）
     **/
    private int[] mergeSort(int[] sourceList) {
        if (sourceList == null || sourceList.length == 0) {
            return new int[0];
        }

        if (sourceList.length == 1) {
            return sourceList;
        }

        int mid = sourceList.length / 2;
        //Arrays.copyOfRange()复制角标0-mid，不包括角标mid的数据
        int[] left = Arrays.copyOfRange(sourceList, 0, mid);
        int[] right = Arrays.copyOfRange(sourceList, mid, sourceList.length);

        int[] sortLeft = mergeSort(left);
        int[] sortRight = mergeSort(right);

        return mergeArrays(sortLeft, sortRight);


    }

    private int[] mergeArrays(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int indexLeft = 0;
        int indexRight = 0;
        for (int i = 0; i < result.length; i++) {
            if (indexLeft >= left.length) {
                result[i] = right[indexRight];
                indexRight++;
                continue;
            } else if (indexRight >= right.length) {
                result[i] = left[indexLeft];
                indexLeft++;
                continue;
            }
            int tempL = left[indexLeft];
            int tempR = right[indexRight];
            if (tempL <= tempR) {
                result[i] = tempL;
                indexLeft++;
            } else {
                result[i] = tempR;
                indexRight++;
            }
        }

        return result;
    }

    //===================================麦粒总数===========================================

    /**
     * 证明命题:n格放的麦粒总数为2^n-1
     * 命题：在国际象棋的64个格子中放置麦粒，第一格放1粒，第二格是第一格的2倍一次类推
     * ，那么第n格是2^(n-1)，n格放的麦粒总数为2^n-1
     * 证明方法：先证明n=1时，命题成立，再证明n=k-1时成立，然后证明n=k也成立（k>1)
     **/
    private boolean prove(int k, Result result) {
        //证明n=1时，命题成立
        if (k == 1) {
            if ((Math.pow(2, 1) - 1) == 1) {
                result.mCurNumber = 1;
                result.mTotalNumber = 1;
                return true;
            } else {
                return false;
            }
        } else {
            //证明n=k-1成立
            boolean proveOfPreviousOne = prove(k - 1, result);
            result.mCurNumber = result.mCurNumber * 2;
            result.mTotalNumber = result.mTotalNumber + result.mCurNumber;
            boolean proveOfCurrentOne = false;
            //证明n=k成立
            if (result.mTotalNumber == (Math.pow(2, k) - 1)) {
                proveOfCurrentOne = true;
            }
            if (proveOfPreviousOne && proveOfCurrentOne) {
                return true;
            } else {
                return false;
            }
        }
    }

    class Result {
        //当前格子的麦粒
        public long mCurNumber = 0;
        //所有格子的麦粒
        public long mTotalNumber = 0;
    }


    //==============================递归组合==========================、、
    private int[] mults;

    /**
     * 获取以某个数为乘积的所有可能组合（不包括1）
     **/
    private void getCombinationOfNumberTimes(int number, ArrayList<Integer> result) {

        if (number == 1) {
            Loger.i(TAG, "" + result);
            return;
        } else {
            for (int i = 0; i < mults.length; i++) {
                ArrayList<Integer> newResult = (ArrayList<Integer>) (result.clone());
                if (number % mults[i] == 0) {
                    int newNumber = number / mults[i];
                    if (newNumber > 0) {
                        newResult.add(mults[i]);
                        getCombinationOfNumberTimes(newNumber, newResult);
                    }
                }
            }
        }

    }
    //==================计算在n个元素的数组中取m个元素的组合数===================

    private int combineCount = 0;
    private void countCombine(){
        ArrayList<String> combineList = new ArrayList<String>();
        combineList.add("中国");
        combineList.add("美国");
        combineList.add("法国");
        combineList.add("俄罗斯");
        combineList.add("英格兰");
//        combineList.add("意大利");
//        combineList.add("巴西");
//        combineList.add("阿根廷");
//        combineList.add("澳大利亚");
//        combineList.add("瑞士");
//        combineList.add("土耳其");
//        combineList.add("伊拉克");
//        combineList.add("伊朗");
//        combineList.add("韩国");
//        combineList.add("日本");
//        combineList.add("荷兰");
//        combineList.add("比利时");
//        combineList.add("乌克兰");
//        combineList.add("乌拉圭");
//        combineList.add("南非");
//        combineList.add("埃及");
//        combineList.add("冰岛");
//        combineList.add("瑞典");
//        combineList.add("希腊");
//        combineList.add("朝鲜");
//        combineList.add("德国");
//        combineList.add("丹麦");
//        combineList.add("西班牙");
//        combineList.add("葡萄牙");
//        combineList.add("牙买加");
//        combineList.add("加拿大");
//        combineList.add("墨西哥");

        combine(combineList,new ArrayList<String>(),2);
        Loger.i(TAG, "CombineCount = "+combineCount);
        combineCount = 0;
    }

    /**
     * 计算n个元素中取m个元素的组合
     * 备注：这里用的是递归法穷举，对应计算方式应为C(m,n) = A(m,n)/m! = (n!/(n-m)!)/m! =n!/((n-m)!*m!);
     *
     * @param combineList 可选的数据list（size = n)
     * @param resultList  选出的组合list
     * @param m           选出的组合list需要的元素个数
     **/
    private void combine(ArrayList<String> combineList, ArrayList<String> resultList, int m) {
        if (resultList == null || resultList.size() == m) {
            Loger.i(TAG, resultList);
            combineCount++;
            return;
        }

        for (int i = 0; i < combineList.size(); i++) {
            ArrayList<String> newResult = (ArrayList<String>) resultList.clone();
            newResult.add(combineList.get(i));
            if(newResult.size()>m){
                break;
            }
            ArrayList<String> newCombineList = new ArrayList<>(combineList.subList(i+1, combineList.size()));
            combine(newCombineList,newResult,m);
        }
    }
}
