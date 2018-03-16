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
 * @date: 2018/3/16 23:55
 * @since: 1.0.0
 */
public class GreatestCommonDivisor {


    /**
     * 求取最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int computer(int a, int b) {

        int max, min;
        max = (a > b) ? a : b;
        min = (a < b) ? a : b;

        if (max % min != 0) {
            return computer(min, max % min);
        } else
            return min;
    }
}
