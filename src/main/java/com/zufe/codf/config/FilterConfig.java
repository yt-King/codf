package com.zufe.codf.config;


import com.zufe.codf.filter.ResponseFilter;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应涛
 * @date 2021/12/5
 * @function：
 */
@Configuration
public class FilterConfig {
    /**
     * 自定义过滤器
     * @param
     * @return
     */

    @Bean
    public FilterRegistrationBean setFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new ResponseFilter());
        filterBean.setOrder(0);
        filterBean.setName("callbackFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }
    /**
     * 把第三方的过滤器包装成一个Spring的Bean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        System.out.println("logout");
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new SingleSignOutFilter());
        bean.setName("CAS Single Sign Out Filter");
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        return bean;
    }
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new AuthenticationFilter());
        bean.setName("CASAuthenticationFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        Map map=new HashMap();
        map.put("serverName","http://150.158.28.238");
        map.put("casServerLoginUrl","http://cas.zufe.edu.cn/cas/login");
        bean.setInitParameters(map);
        return bean;
    }
    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new Cas20ProxyReceivingTicketValidationFilter());
        bean.setName("CASValidationFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        Map map=new HashMap();
        map.put("serverName","http://150.158.28.238");
        map.put("casServerUrlPrefix","http://cas.zufe.edu.cn/cas");
        bean.setInitParameters(map);
        return bean;
    }
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new HttpServletRequestWrapperFilter());
        bean.setName("CASHttpServletRequestWrapperFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(4);
        return bean;
    }

}
