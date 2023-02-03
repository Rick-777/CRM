package com.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity

/**
 * Configuration of Spring Security
 */
@Configuration
public class MySpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder) throws Exception{
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        super.configure(web);
    }
}
