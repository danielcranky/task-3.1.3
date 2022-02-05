package task313.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import task313.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler successUserHandler;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")  // указываем страницу с формой логина
                .successHandler(successUserHandler)  //указываем логику обработки при логине
                .loginProcessingUrl("/login")   // указываем action с формы логина
                .usernameParameter("j_email")    // Указываем параметры логина и пароля с формы логина
                .passwordParameter("j_password")
                .permitAll();   // даем доступ к форме логина всем

        http.logout()
                .permitAll()    // разрешаем делать логаут всем
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // указываем URL логаута
                //.logoutSuccessUrl("/login?logout")  // указываем URL при удачном логауте
                .and().csrf().disable();    //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)

        http
                .authorizeRequests()    // делаем страницу регистрации недоступной для авторизированных пользователей
                .antMatchers("api/users/**").hasRole("ADMIN")
                .antMatchers("api/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/login").anonymous()  //страницы аутентификаци доступна всем
                .antMatchers("/hello").access("hasAnyRole('ADMIN')").anyRequest().authenticated();  // защищенные URL
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
