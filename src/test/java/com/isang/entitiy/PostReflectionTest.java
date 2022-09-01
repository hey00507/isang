package com.isang.entitiy;


import com.isang.api.domain.Post;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class PostReflectionTest {

    @Test
    void getClassFields(){
    Class postClass = Post.class;
        Field[] fields = postClass.getDeclaredFields();

        Arrays.stream(fields).forEach(System.out::println);

        System.out.println(fields[0].getName());
    }

}
