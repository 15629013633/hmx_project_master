package com.hmx.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        Map<String, List<Student>> paramList = new HashMap<String, List<Student>>();
        Student student = new Student();
        CallbackDto dto = new CallbackDto("1",param,paramList,"http:www",0,1);
        System.out.println(dto.getTransactionId());
        System.out.println(dto.getUrl());
    }


}
