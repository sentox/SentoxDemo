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
 * 描述：
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
        Result result = new Result();
        mTvFunction.setText("证明：n格放的麦粒总数为2^n-1");
        mTvResult.setText("证明命题为：" + prove(63, result) + ",\n总数为：" + result.mTotalNumber);
        //=====================计算乘积组合=======================//
        int testNumber = 20;
        mults = new int[testNumber - 2];
        for (int i = 0; i < testNumber - 2; i++) {
            mults[i] = i + 2;
        }
        getCombinationOfNumberTimes(testNumber, new ArrayList<Integer>());
        //=====================归并排序====================================//
        int[] to_sort = {3434, 3356, 67, 12334, 878667, 387};
        int[] sortResult = mergeSort(to_sort);
        String string = "";
        for (int i = 0; i < sortResult.length; i++) {
            string = string +","+ sortResult[i];
        }
        Loger.i(TAG, string);
    }

    //======================归并排序=============================================

    /**
     * 归并排序算法(从小到大）
     **/
    private int[] mergeSort(int[] sourceList) {
        if (sourceList == null || sourceList.length == 0) {
            return new int[0];
        }

        if(sourceList.length == 1){
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
}
