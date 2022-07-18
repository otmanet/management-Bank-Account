package com.example.demo.ConfigSecurity;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private  LoginSuccessHandler loginSuccessHandler;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* TODO Auto-generated method stub */
		PasswordEncoder passEncoder = encoder();
		System.out.println("**************************");
		System.out.println(passEncoder.encode("1234"));
		System.out.println("*************************");

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select email as princibal , password  as Credentials,'true' as enabled"
						+ " from users where email=?")
				.authoritiesByUsernameQuery("select email as princibal , role as role  "
						+ "from users_roles where email=?")
				.passwordEncoder(passEncoder)
				.rolePrefix("ROLE_");
		// auth.inMemoryAuthentication().withUser("admin").password(passenEncoder().encode("1234")).roles("USER",
		// "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/GestionAccount").hasRole("USER")
				.antMatchers("/").anonymous()
				.antMatchers("/login").anonymous()
				.antMatchers("/registerform").permitAll()
				.antMatchers("/confirm").permitAll()
				.antMatchers("/client**/**/**", "/save/client", "/formClient", "/comptes**/**", "/compteCourant/",
						"/compteEpargne/", "/formCompteC/", "/save/CompteCourant", "/compteCourant/delete",
						"/formCompteC", "/compteCourant/edit", "/formCompteE",
						"/save/CompteEpargne", "/CompteEpargne**/**")
				.hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/login").successHandler(loginSuccessHandler)
				.and()
				.csrf().disable()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
				.logout().clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
	}
}
