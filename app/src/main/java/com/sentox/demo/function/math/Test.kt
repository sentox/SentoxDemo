package com.sentox.demo.function.math

import android.widget.ArrayAdapter
import com.sentox.demo.utils.TAG
import java.util.*
import kotlin.collections.HashMap

/**
 * 描述：
 * 说明：
 * Created by Sentox
 * Created on 2022/5/23
 */
class Test {
    fun exist(board: Array<CharArray>, word: String): Boolean {
        var flag = false

        val wordArray = word.toCharArray()
        var firstWord = wordArray[0]
        for ((i, array) in board.withIndex()) {
            for ((j, char) in array.withIndex()) {
                if (char == firstWord) {
                    val map = hashMapOf<String, Char>()
                    map["$i$j"] = char
                    flag = tryToGetWord(i, j, 1, wordArray, board,map)
                    if (flag) {
                        break
                    }
                }
            }
            if(flag){
                break
            }
        }
        return flag
    }

    fun tryToGetWord(i: Int, j: Int, nextPos: Int, wordArray: CharArray, board: Array<CharArray>,map:HashMap<String,Char>): Boolean {
        var result = false
        if (nextPos >= wordArray.size) {
            result = true
        } else {
            var nextChar = wordArray[nextPos]
            var top = false
            if (i - 1 >= 0 && !map.containsKey("${i - 1}$j") && nextChar == board[i - 1][j]) {
                val subMap = HashMap<String,Char>()
                subMap.putAll(map)
                subMap["${i - 1}$j"] = nextChar
                top = tryToGetWord(i - 1, j, nextPos + 1, wordArray, board,subMap)
            }
            var down = false
            if (i + 1 < board.size && !map.containsKey("${i + 1}$j") && nextChar == board[i + 1][j]) {
                val subMap = HashMap<String,Char>()
                subMap.putAll(map)
                subMap["${i + 1}$j"] = nextChar
                down = tryToGetWord(i + 1, j, nextPos + 1, wordArray, board,subMap)
            }
            var left = false
            if (j - 1 >= 0 && !map.containsKey("$i${j - 1}") && nextChar == board[i][j - 1]) {
                val subMap = HashMap<String,Char>()
                subMap.putAll(map)
                subMap["$i${j - 1}"] = nextChar
                left = tryToGetWord(i, j - 1, nextPos + 1, wordArray, board,subMap)
            }
            var right = false
            if (j + 1 < board[i].size && !map.containsKey("$i${j + 1}") && nextChar == board[i][j + 1]) {
                val subMap = HashMap<String,Char>()
                subMap.putAll(map)
                subMap["$i${j + 1}"] = nextChar
                right = tryToGetWord(i, j+1, nextPos + 1, wordArray, board,subMap)
            }
            result = top || down || left || right
        }

        return result
    }

    fun exchange(nums: IntArray): IntArray {
        val a1 = arrayListOf<Int>()
        val a2 = arrayListOf<Int>()
        for(num in nums){
            if(num%2>0){
                a1.add(num)
            }else{
                a2.add(num)
            }
        }
        a1.addAll(a2)
        return a1.toIntArray()
    }
}