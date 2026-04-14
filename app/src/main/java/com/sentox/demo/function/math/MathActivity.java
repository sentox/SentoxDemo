package com.sentox.demo.function.math;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sentox.demo.R;
import com.sentox.demo.databinding.ActivityMathBinding;
import com.sentox.demo.function.base.activity.BaseActivity;
import com.sentox.demo.function.base.log.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import androidx.annotation.Nullable;

/**
 * 描述：验证Activity
 * 说明：
 * Created by Sentox
 * Created on 2018/12/19
 */
public class MathActivity extends BaseActivity<ActivityMathBinding> {

    private static final String TAG = "MathResult";

    private TextView mTvFunction;
    private TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvFunction = findViewById(R.id.tv_math_function);
        mTvResult = findViewById(R.id.tv_math_result);
        mTvFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                char[][] a = new char[3][4];
//                a[0] = new char[]{'A', 'B', 'C', 'E'};
//                a[1] = new char[]{'S', 'F', 'E', 'S'};
//                a[2] = new char[]{'A', 'D', 'E', 'E'};
//                boolean flag = new Test().exist(a, "ABCESEEEFS");

                mTvResult.setText(findNthDigit(11) + "");
            }
        });

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
            L.info(TAG, "T1:线程启动");
            boolean flag = true;
            int index = 0;
            try {
                while (flag) {
                    index++;
                    L.info(TAG, "T1:" + index);
                    Thread.sleep(1000);
                    if (mFlagThreadStart) {
                        mT2.join();
                        flag = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            L.info(TAG, "T1:线程结束");
        }
    });

    Thread mT2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                L.info(TAG, "T2:线程启动");
                Thread.sleep(4000);
                L.info(TAG, "T2：线程结束");
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
            L.info(TAG, "" + result);
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

    private void countCombine() {
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

        combine(combineList, new ArrayList<String>(), 2);
        L.info(TAG, "CombineCount = " + combineCount);
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
            L.info(TAG, resultList);
            combineCount++;
            return;
        }

        for (int i = 0; i < combineList.size(); i++) {
            ArrayList<String> newResult = (ArrayList<String>) resultList.clone();
            newResult.add(combineList.get(i));
            if (newResult.size() > m) {
                break;
            }
            ArrayList<String> newCombineList = new ArrayList<>(combineList.subList(i + 1, combineList.size()));
            combine(newCombineList, newResult, m);
        }
    }

    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left,
                                int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }
        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left,
                inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1,
                inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 机器人运动范围
     **/
    public int movingCount(int m, int n, int k) {
        boolean[][] board = new boolean[m][n];
        return dfs(board, m, n, k, 0, 0);
    }

    public int dfs(boolean[][] board, int m, int n, int k, int i, int j) {
        if (i >= m || j >= n || getLimit(i, j) > k || board[i][j]) {
            return 0;
        }
        board[i][j] = true;
        return 1 + dfs(board, m, n, k, i + 1, j) + dfs(board, m, n, k, i, j + 1);
    }

    public int getLimit(int i, int j) {
        int ri = i % 10 + i / 10;
        if (i == 100)
            ri = 1;
        int rj = j % 10 + j / 10;
        if (j == 100) {
            rj = 1;
        }
        return ri + rj;
    }


    /**
     * 计算二进制有几个1
     **/
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            res += n & 1;
            n = n >>> 1;
        }
        return res;
    }

    /**
     * 快速幂运算
     **/
    public double myPow(double x, int n) {
        long N = n;
        return N > 0 ? quickPow(x, N) : 1d / quickPow(x, -N);
    }

    public double quickPow(double x, long n) {
        if (n == 0) {
            return 1.0d;
        }
        double y = quickPow(x, n / 2);
        return n % 2 == 0 ? y * y : y * y * x;
    }

    /**
     * 根据输入打印数字
     **/
    public int[] printNumbers(int n) {
        int max = 9;
        while (n > 1) {
            max = max * 10 + 9;
            n--;
        }
        int[] res = new int[max];
        for (int i = 1; i <= max; i++) {
            res[i - 1] = i;
        }
        return res;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 删除一个节点
     **/
    public ListNode deleteNode(ListNode head, int val) {
        ListNode temp = head;
        ListNode last = head;
        boolean isFirst = true;
        while (temp != null) {
            if (temp.val != val) {
                last = temp;
                temp = temp.next;
            } else {
                if (isFirst) {
                    head = last.next;
                } else {
                    last.next = temp.next;
                }
                break;
            }
            isFirst = false;
        }
        return head;
    }

    /**
     * 判断这个字符串是否为数字
     **/
    public boolean isNumber(String s) {
        boolean isNumber = false;
        boolean isDot = false;
        boolean isE = false;
        s = s.trim();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if ((temp < '0' || temp > '9') && temp != '.' && temp != 'e' && temp != 'E' && temp != '+' && temp != '-') {
                return false;
            } else if (isInt(temp)) {
                if (s.charAt(0) != 'e' && s.charAt(0) != 'E') {
                    isNumber = true;
                } else {
                    isNumber = false;
                }
            } else if (temp == '.' && isDot) {
                return false;
            } else if (temp == '.' && !isDot && !isE) {
                isDot = true;
                if (i + 1 < s.length() && (s.charAt(i + 1) == 'e' || s.charAt(i + 1) == 'E')) {
                    return false;
                }
            } else if ((temp == 'e' || temp == 'E') && !isE && isNumber) {
                isE = true;
                isNumber = false;
            } else if ((temp == '+' || temp == '-') && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {

            } else {
                isNumber = false;
            }
        }
        return isNumber;
    }

    public boolean isInt(char c) {
        return c >= '0' && c <= '9';
    }


    /**
     * 输出倒数第k个节点
     **/
    public ListNode getKthFromEnd(ListNode head, int k) {
        int count = 1;
        ListNode result = head;
        while (head != null) {

            if (count > k && result != null) {
                result = result.next;
            }
            head = head.next;
            count++;
        }
        return result;
    }

    /**
     * 反转链表
     **/
    public ListNode reverseList(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.add(head);
            head = head.next;
        }
        if (stack.empty()) {
            return null;
        }
        ListNode newHead = stack.pop();
        ListNode end = newHead;
        while (!stack.empty()) {
            ListNode temp = stack.pop();
            end.next = temp;
            end = temp;
        }
        end.next = null;
        return newHead;
    }

    /**
     * 合并链表
     **/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        if (l1 == null || l2 == null) {
            if (l1 != null) {
                return l1;
            }
            if (l2 != null) {
                return l2;
            }
            return null;
        }
        if (l1.val < l2.val) {
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }
        ListNode next = head;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 == null) {
                next.next = l1;
                next = l1;
                l1 = l1.next;
                continue;
            }
            if (l1 == null && l2 != null) {
                next.next = l2;
                next = l2;
                l2 = l2.next;
                continue;
            }
            if (l1.val > l2.val) {
                next.next = l2;
                next = l2;
                l2 = l2.next;
            } else {
                next.next = l1;
                next = l1;
                l1 = l1.next;
            }
        }
        return head;
    }

    /**
     * 判断树是否子结构
     **/
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        boolean flag = false;
        if (A == null || B == null) {
            return flag;
        }
        List<TreeNode> list = new ArrayList<>();
        getSameNode(A, B.val, list);
        for (int i = 0; i < list.size(); i++) {
            flag = compare(list.get(i), B);
            if (flag) {
                break;
            }
        }
        return flag;
    }

    public void getSameNode(TreeNode A, int target, List<TreeNode> list) {
        if (A == null) {
            return;
        }
        if (A.val == target) {
            list.add(A);
        }
        getSameNode(A.left, target, list);
        getSameNode(A.right, target, list);
    }

    public boolean compare(TreeNode A, TreeNode B) {
        if (A == null && B == null) {
            return true;
        } else if (A != null && B != null) {
            if (A.val == B.val) {
                return compare(A.left, B.left) && compare(A.right, B.right);
            } else {
                return false;
            }
        } else {
            //防止出现子树没有子节点而母树仍有子节点的问题
            return A != null && B == null;
        }
    }

    /**
     * 镜像二叉树
     **/
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    /**
     * 判断是否是镜像树
     **/
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return compareMirror(root.left, root.right);
    }

    public boolean compareMirror(TreeNode A, TreeNode B) {
        if (A == null && B == null) {
            return true;
        } else if (A != null && B != null) {
            if (A.val == B.val) {
                return compareMirror(A.left, B.right) && compareMirror(A.right, B.left);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 顺时针打印矩阵
     **/
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int lineSize = matrix[0].length;
        int downSize = matrix.length;
        int size = downSize * lineSize;
        int[] result = new int[size];
        int top = 0;
        int bottom = downSize - 1;
        int left = 0;
        int right = lineSize - 1;
        int count = 0;
        while (true) {
            for (int i = left; i <= right; i++) {
                result[count++] = matrix[top][i];
            }
            if (++top > bottom) break;
            for (int i = top; i <= bottom; i++) {
                result[count++] = matrix[i][right];
            }
            if (--right < left) break;
            for (int i = right; i >= left; i--) {
                result[count++] = matrix[bottom][i];
            }
            if (--bottom < top) break;
            for (int i = bottom; i >= top; i--) {
                result[count++] = matrix[i][left];
            }
            if (++left > right) break;
        }
        return result;
    }

    class MinStack {

        private Stack<Integer> A, B;

        public MinStack() {
            A = new Stack<>();
            B = new Stack<>();
        }

        public void push(int x) {
            A.push(x);
            if (B.empty() || B.peek() >= x) {
                B.add(x);
            }
        }

        public void pop() {
            int x = A.pop();
            if (B.peek() == x) {
                B.pop();
            }
        }

        public int top() {
            return A.peek();
        }

        public int min() {
            return B.peek();

        }
    }

    /**
     * 栈的压入弹出序列
     **/
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length != popped.length || pushed.length == 0) {
            return true;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int j = 0;
        for (int i = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);
            while (j < popped.length) {
                if (!stack.empty() && popped[j] == stack.peek()) {
                    stack.pop();
                    j++;
                } else {
                    break;
                }
            }
        }
        return stack.empty();
    }

    /**
     * 层序遍历
     **/
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        List<List<Integer>> a = new ArrayList<>();
        return result;
    }

    /**
     * 层序遍历，返回从左到右
     **/
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<SubTreeNode> queue = new LinkedList<SubTreeNode>();
        queue.add(new SubTreeNode(root, 0));
        while (!queue.isEmpty()) {
            SubTreeNode node = queue.poll();
            if (node.level + 1 - result.size() > 0)
                result.add(new ArrayList<Integer>());
            result.get(node.level).add(node.val);
            int next = node.level + 1;
            if (node.left != null)
                queue.add(new SubTreeNode(node.left, next));
            if (node.right != null) {
                queue.add(new SubTreeNode(node.right, next));
            }
        }
        return result;
    }

    class SubTreeNode {
        int level = 0;
        int val = 0;
        TreeNode left;
        TreeNode right;

        public SubTreeNode(TreeNode node, int level) {
            if (node == null) {
                return;
            }
            this.level = level;
            val = node.val;
            left = node.left;
            right = node.right;
        }
    }

    /**
     * 层序遍历，返回之字形
     **/
    public List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        boolean ltr = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> temp = new LinkedList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();
                if (result.size() % 2 == 0) {
                    temp.addLast(node.val);
                } else {
                    temp.addFirst(node.val);
                }
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            result.add(temp);
        }
        return result;
    }

    /**
     * 二叉树后序遍历序列
     **/
    public boolean verifyPostorder(int[] postorder) {
        return judge(0, postorder.length - 1, postorder);
    }

    public boolean judge(int start, int end, int[] postorder) {
        if (start >= end) return true;
        int middle = start;
        while (postorder[middle] < postorder[end]) {
            middle++;
        }
        int left = middle;
        while (postorder[middle] > postorder[end]) {
            middle++;
        }
        return middle == end && judge(start, left - 1, postorder) && judge(left, end - 1, postorder);
    }

    /**
     * 二叉树中和为某一值的路径
     **/
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int target) {

        cal(root, 0, target, new ArrayList<Integer>());
        return result;
    }

    public void cal(TreeNode root, int count, int target, ArrayList<Integer> list) {
        if (root == null) return;
        count = count + root.val;
        list.add(root.val);
        if (count == target && root.left == null && root.right == null) {
            List<Integer> fin = new ArrayList<>();
            fin.addAll(list);
            result.add(fin);
        } else {
            if (root.left != null) cal(root.left, count, target, list);
            if (root.right != null) cal(root.right, count, target, list);
        }
        list.remove(list.size() - 1);
    }

    /**
     *  复制复杂链表
     * **/
//    public Node copyRandomList(Node head) {
//        Node temp = head;
//        HashMap<Node,Node> map = new HashMap<>();
//        while(temp!=null){
//            map.put(temp,new Node(temp.val));
//            temp = temp.next;
//        }
//        temp = head;
//        while(temp!=null){
//            if(temp.next !=null)
//            map.get(temp).next = map.get(temp.next);
//            if(temp.random != null)
//            map.get(temp).random = map.get(temp.random);
//            temp = temp.next;
//        }
//        return map.get(head);
//    }
//
//    class Node {
//        int val;
//        Node next;
//        Node random;
//
//        public Node(int val) {
//            this.val = val;
//            this.next = null;
//            this.random = null;
//        }
//    }


    /**
     * 穷举法显示所有String组合，set去重
     **/
    Set<String> result1 = new HashSet<>();
    char[] array;
    int num = 0;

    public String[] permutation(String s) {
        array = s.toCharArray();
        num = array.length;
        prient(new StringBuilder(), new boolean[num]);
        Iterator iterator = result1.iterator();
        int i = 0;
        String[] res = new String[result1.size()];
        while (iterator.hasNext()) {
            res[i] = (String) iterator.next();
            i++;
        }
        return res;
    }

    public void prient(StringBuilder builder, boolean[] flagList) {
        if (builder.length() == num) {
            result1.add(builder.toString());
            return;
        }
        for (int i = 0; i < num; i++) {
            if (flagList[i]) continue;
            builder.append(array[i]);
            flagList[i] = true;
            prient(builder, flagList);
            builder.deleteCharAt(builder.length() - 1);
            flagList[i] = false;
        }
    }

    /**
     * 数组中出现一半的数字
     **/
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxCount = 1;
        int maxNum = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(num)) {
                int count = map.get(num) + 1;
                map.put(num, count);
                if (maxCount < count) {
                    maxCount = count;
                    maxNum = num;
                }
            } else {
                map.put(num, 1);
            }
        }
        return maxNum;
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        int[] list = mergeSort(arr);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = list[i];
        }
        return result;
    }

    /**
     * 连续子数组最大和
     **/
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] + Math.max(nums[i - 1], 0);
            res = Math.max(nums[i], res);
        }
        return res;
    }

    public int findNthDigit(int n) {
        n++;
        int place = 0;
        int charCount = 0;
        int res = 0;
        while (charCount < n) {
            place++;
            charCount = charCount + getCharCountByPlace(place);
        }
        charCount = charCount - getCharCountByPlace(place);
        charCount = n - charCount;
        int first = getFirstNumByPlace(place);
        int extraNum = charCount / place;
        if (extraNum > 0) {
            first = first + extraNum - 1;
        } else if (charCount == 0) {
            first = first - 1;
            place--;
        }
        charCount = charCount - charCount / place * place;
        if (charCount == 0) {
            res = (first + "").charAt(place - 1) - '0';
        } else {
            first++;
            res = (first + "").charAt(charCount - 1) - '0';
        }
        return res;
    }

    public int getFirstNumByPlace(int place) {
        return place > 1 ? (int) Math.pow(10, place - 1) : 0;
    }

    public int getCharCountByPlace(int place) {
        if (place == 1) {
            return 10;
        } else {
            return (int) ((0.9 * Math.pow(10, place)) * place);
        }
    }

    public int[] singleNumbers(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum ^= i;
        }
        int h = 1;
        while ((h & sum) ==0){
            h <<= 1;
        }
        int a = 0;
        int b = 0;
        int[] res = new int[2];
        for (int i : nums) {
            if ((i & h)!=0){
                a ^= i;
            }else{
                b ^= i;
            }
        }
        res[0] = a;
        res[1] = b;
        return res;
    }


}
