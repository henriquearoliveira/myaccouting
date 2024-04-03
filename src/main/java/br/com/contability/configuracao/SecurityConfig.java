package br.com.contability.configuracao;

import br.com.contability.business.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//	@Autowired TODO ISSO DÁ REFERENCIA CIRCULAR
//	public void configAuthentication(AuthenticationManagerBuilder auth/*, AuthenticationManagerBuilder authMaster*/)
//			throws Exception {
//
//		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//			.usersByUsernameQuery(
//				"SELECT email AS username, senha AS password, ativo AS enabled FROM usuario WHERE email = ?")
//			.authoritiesByUsernameQuery(
//				"SELECT email AS username, IF(administrador = true, 'ADMINISTRADOR', 'ADMIN') AS role FROM usuario WHERE email = ?");
//
//	}
	 /* auth.inMemoryAuthentication()
	 .withUser("joao").password("joao").roles("CADASTRAR_VINHO").and()
	 .withUser("maria").password("maria").roles("CADASTRAR_VINHO",
	 "LISTAR_VINHO");
	 
	 authMaster.jdbcAuthentication().dataSource(dataSource)
	 .usersByUsernameQuery(
	 "SELECT email AS username, senha_master AS password, ativo AS enabled FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, IF(administrador = true, 'ADMINISTRADOR', 'ADMIN') AS role FROM usuario WHERE email = ?"
	 );
	 
	 
	 client.jdbcAuthentication().dataSource(dataSource) .usersByUsernameQuery(
	 "SELECT email AS username, senha AS password, (SELECT TRUE) AS enable FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, (SELECT 'ADMINISTRADOR') AS role FROM usuario WHERE email = ?"
	 );
	 
	 clientMaster.jdbcAuthentication().dataSource(dataSource)
	 .usersByUsernameQuery(
	 "SELECT email AS username, senha_master AS password, ativo AS enabled FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, (SELECT 'ADMINISTRADOR') AS role FROM usuario WHERE email = ?"
	 );
	 
	 
	 }
	 

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("joao")
				.password(
						"0cc357ec192ad6a4432707492649ced1485ea760ebfbcb31674d1753f2c914aa9d9609aef9130856c93485628b510dbf37804443023a11da438e58411465a537")
				.roles("USER");

	}
	*/

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioJpaRepository) {
        return username -> usuarioJpaRepository.getByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username '%s' not found", username)));
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/layout/**");
//    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers("/layout/**");
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new ShaPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/teste/**", "/cadastrar/**", "/esqueceusenha/**", "/alterasenha/**", "/h2-console/**", "/layout/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .tokenValiditySeconds(1209600)
                        .tokenRepository(persistentTokenRepository()))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .deleteCookies("auth_code", "JSESSIONID")
                        .invalidateHttpSession(true));

//        http.authorizeRequests().antMatchers("/login/**", "/teste/**", "/cadastrar/**", "/esqueceusenha/**", "/alterasenha/**", "/h2-console/**").permitAll().anyRequest().authenticated()
//                .and().httpBasic()
//                .and().formLogin().loginPage("/login")/*.failureUrl("/login")*/.usernameParameter("username").passwordParameter("password")
//                .defaultSuccessUrl("/", true)
//                .and().rememberMe()
//                .tokenValiditySeconds(1209600)
//                .tokenRepository(persistentTokenRepository())
//                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);

        //http.csrf(AbstractHttpConfigurer::disable);
        //http.csrf().disable();
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
        //http.headers().frameOptions().disable();
        //http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));


//        http.authorizeRequests()
//                .antMatchers("/h2-console/*").permitAll()
//                .antMatchers("/auth").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests().antMatchers("/login/**", "/teste/**", "/cadastrar/**", "/esqueceusenha/**", "/alterasenha/**", "/h2-console/**").permitAll().anyRequest().authenticated()
//                .and().httpBasic()
//                .and().formLogin().loginPage("/login")/*.failureUrl("/login")*/.usernameParameter("username").passwordParameter("password")
//                .defaultSuccessUrl("/", true)
//                .and().rememberMe()
//                .tokenValiditySeconds(1209600)
//                .tokenRepository(persistentTokenRepository())
//                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
//
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
//        http.headers().frameOptions().disable();

        /*
         * http.logout().logoutUrl("/logout").invalidateHttpSession(true).
         * clearAuthentication(true);
         *
         * http.logout().logoutRequestMatcher(new
         * AntPathRequestMatcher("/logout"));
         *
         * http.logout().logoutUrl("/logout").clearAuthentication(true).
         * logoutSuccessUrl("/login");
         *
         *
         * http .authorizeRequests() .antMatchers("/usuario").hasRole("USER")
         * .anyRequest().authenticated() .and() .formLogin()
         * .loginPage("/login") .permitAll() .and() .logout()
         * .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
         *
         *
         * http.csrf().disable(); começo teste
         *
         *
         * http.authorizeRequests()
         * .antMatchers("/usuario").access("hasRole('ROLE_USER')") .and()
         * .formLogin().loginPage("/login") .defaultSuccessUrl("/usuario")
         * .failureUrl("/login?error")
         * .usernameParameter("username").passwordParameter("password") .and()
         * .logout().logoutSuccessUrl("/login");
         *
         * final teste
         *
         * http.authorizeRequests().antMatchers("/usuario/**").permitAll().
         * anyRequest().authenticated(); //.and().httpBasic();
         *
         * http.authorizeRequests().antMatchers("/login/**").permitAll().and().
         * httpBasic();
         *
         * http .authorizeRequests()
         * .antMatchers("/usuario").hasRole("USER").anyRequest().authenticated()
         * ;
         *
         * http.logout() .logoutRequestMatcher(new
         * AntPathRequestMatcher("/logout")) .logoutSuccessUrl("/login");
         *
         * http .authorizeRequests()
         * .antMatchers("/usuario").hasRole("USER").anyRequest().authenticated()
         * http .authorizeRequests().antMatchers("/usuario",
         * "/login").permitAll().anyRequest().authenticated().and().httpBasic()
         * .and() .formLogin() .loginPage("/login") .permitAll() .and()
         * .logout() .logoutRequestMatcher(new
         * AntPathRequestMatcher("/logout"));
         */

//    }

    /* REMEMBER ME DAS PÁGINAS, NÃO FICA DESLOGANDO */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
