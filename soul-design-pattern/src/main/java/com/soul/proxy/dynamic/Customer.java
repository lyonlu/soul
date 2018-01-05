package com.soul.proxy.dynamic;

import com.soul.proxy.statics.CarSales;
import com.soul.proxy.statics.Sales;

/**
 * @author: sumy
 * @date: 2018/1/5
 * @since: 1.0.0
 */
public class Customer {

    public static void main(String[]args) {
        Sales proxy= new SalesInvocationHandler().bind(new CarSales());
        proxy.sell();
    }
}
