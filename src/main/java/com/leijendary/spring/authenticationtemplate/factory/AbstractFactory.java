package com.leijendary.spring.authenticationtemplate.factory;

import org.modelmapper.ModelMapper;

import static com.leijendary.spring.authenticationtemplate.util.SpringContext.getBean;

public abstract class AbstractFactory {

    protected static final ModelMapper MAPPER = getBean(ModelMapper.class);
}
