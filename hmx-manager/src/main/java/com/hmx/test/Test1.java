package com.hmx.test;

import java.math.BigDecimal;

public class Test1 {
    public static void main(String[] args) {
        if(new BigDecimal(24.8).compareTo(new BigDecimal("0.00"))>0){
            System.out.println(1);
        }else{
            System.out.println(2);
        }
    }
}
