package com.isang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class IsangApplicationTests {

    static String test =
                    """
                    안녕
                    하십니까
                    이건 
                    테스트 입니다.
                    
                    핳
                    
                    """;
    @Test
    void contextLoads() {
        // 스프링 컨텍스트를 테스트하기 위한 메서드
    }

    public static void main(String[] args) {
        System.out.println(test);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(IsangApplication.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            System.out.println("bean = " + bean);
        }
    }

}
