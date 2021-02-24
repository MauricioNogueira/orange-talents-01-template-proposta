package br.com.zup.proposta.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests(authorizeRequests ->
        authorizeRequests
                .antMatchers(HttpMethod.GET, "/api/propostas/**").hasAuthority("SCOPE_proposta")
//                .antMatchers(HttpMethod.GET, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:read")
//                .antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_cartoes")
                .antMatchers(HttpMethod.POST, "/api/propostas/**").hasAuthority("SCOPE_proposta")
                .anyRequest().authenticated()
		).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
