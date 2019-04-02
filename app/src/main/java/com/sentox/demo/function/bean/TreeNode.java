package com.sentox.demo.function.bean;

import java.util.HashMap;

/**
 * 描述：前缀树节点类
 * 说明：
 * Created by Sentox
 * Created on 2019/2/19
 */
public class TreeNode {

    /**
     *  节点名称（当前节点字母）
     * **/
    public char mLabel;

    /**
     *  子节点哈希表
     *  使用哈希表便于快速确认是否有子节点
     * **/
    public HashMap<Character,TreeNode> mSons = null;

    /**
     * 从树的根部到本节点组成的词缀
     * **/
    public String mPrefix = null;

    /**
     *  词条解释（如果到本节点能组成单词的话）
     * **/
    public String mExplanation = null;

    public TreeNode(char label,String prefix,String explanation){
        mLabel = label;
        mPrefix = prefix;
        mExplanation = explanation;
        mSons = new HashMap<>();
    }

}
