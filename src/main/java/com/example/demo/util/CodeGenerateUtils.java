package com.example.demo.util;

import java.util.UUID;

/**
 * @author liuwei
 * @version 1.0
 **/
public class CodeGenerateUtils {
    public static String getRandomCode(){
        return UUID.randomUUID().toString();
    }
}
