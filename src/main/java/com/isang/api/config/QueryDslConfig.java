package com.isang.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    public EntityManager manager;


    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(manager);
    }
}
