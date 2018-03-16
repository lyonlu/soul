/*
 * *
 *  * Copyright (c) 2017-${year} soul
 *  * All rights reserved.
 *  *
 *  * Permission is hereby granted, free  of charge, to any person obtaining
 *  * a  copy  of this  software  and  associated  documentation files  (the
 *  * "Software"), to  deal in  the Software without  restriction, including
 *  * without limitation  the rights to  use, copy, modify,  merge, publish,
 *  * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  * permit persons to whom the Software  is furnished to do so, subject to
 *  * the following conditions:
 *  *
 *  * The  above  copyright  notice  and  this permission  notice  shall  be
 *  * included in all copies or substantial portions of the Software.
 *  *
 *
 */

package com.soul.primary;

/**
 * @author: sumy
 * @date: 2018/3/17 0:02
 * @since: 1.0.0
 */
public class LeastCommonMultiple {

    /**
     * 获取最小公倍数
     *
     * @param a
     * @param b
     * @return
     */
    public static int computer(int a, int b) {
        return a * b / GreatestCommonDivisor.computer(a, b);
    }


    public static void main(String[] args) {
        System.out.println("最大公约数：" + GreatestCommonDivisor.computer(34, 258));
        System.out.println("最小公倍数：" + LeastCommonMultiple.computer(34, 258));
    }
}
