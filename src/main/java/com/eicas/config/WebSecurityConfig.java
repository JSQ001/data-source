package com.eicas.config;

import com.eicas.filter.TokenAuthenticationFilter;
import com.eicas.handler.*;
import com.eicas.service.UserInfoService;
import com.eicas.service.impl.UserInfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    CustomizeAccessDeniedHandler accessDeniedHandler;

    @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * 关闭跨站请求防护
         */
        http.cors().and().csrf().disable();
        /*
         * 登录处理
         */
        http.formLogin()
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        /*
         * 异常处理(权限拒绝、登录失效)
         */
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);


        http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        /*
         * 登出处理
         */
        http.logout().permitAll().logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID");
        /*
         * 会话管理
         */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*
         * 过滤请求
         */
        http.authorizeRequests()
                .antMatchers(
                        "/login",
                        "/oauth/login",
                        "/api/oauth/getToken",
                        "/register",
                        "/logout"
                ).anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**"
                ).permitAll()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .antMatchers(HttpMethod.GET, // Swagger的资源路径需要允许访问
                        "/",
                        "/swagger-ui.html",
                        "/swagger-ui/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoServiceImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
