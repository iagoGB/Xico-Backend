package br.com.smd.xico.authentication;

import static br.com.smd.xico.authentication.SecurityConstants.EXPIRATION_TIME;
import static br.com.smd.xico.authentication.SecurityConstants.HEADER_STRING;
import static br.com.smd.xico.authentication.SecurityConstants.SECRET;
import static br.com.smd.xico.authentication.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.smd.xico.dto.AuthResponse;
import br.com.smd.xico.models.UserModel;
import br.com.smd.xico.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	// Gerenciador de autenticação
	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager= authenticationManager;
	}
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		this.authenticationManager= authenticationManager;
		this.userRepository = userRepository;

	}
	// Tentativa de autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException 
	{	// Tentar passar os valores que vem do Json para um objeto usuário
		try {
			UserModel usuario = new ObjectMapper().readValue(request.getInputStream(),UserModel.class);
			System.out.println("Transformou para usuário "+ usuario.toString());
			// Provavelmente será necessário  passar a lista de autorizações abaixo (getAuthorities()). Rever depois
			// Solicita a autenticação para o gerenciador
			return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken( usuario.getUsername(), usuario.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	// Quando a autenticação é bem sucedida, este método é invocado para gerar os dados do token
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult
	) throws IOException, ServletException {
		System.out.println("Entrou no método autenticação bem sucedida");
		User us = ((User) authResult.getPrincipal());
		String username = us.getUsername();
		Collection<GrantedAuthority> u = us.getAuthorities();
		Optional<UserModel> findByEmail = userRepository.findByEmail(username);

		// Verifica qual role o usuário tem através do toString,
		// então da um replace nos dados do objeto, deixando apenas o valor da role.
		String r =  u.toString().replace("[Role [roleName=ROLE_", "").replace("]]", "");
		String token = Jwts
				.builder()
				.setSubject(username)
				.setExpiration(new Date (System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		String bearerToken = (TOKEN_PREFIX + token);
		response.setContentType("application/json;charset=UTF-8");
		AuthResponse respostaAutenticacao = AuthResponse.builder()
			.id(findByEmail.get().getId())
			.token(bearerToken)
			.role(r)
			.username(username)
			.build();
		response.getWriter().write(objectMapper.writeValueAsString(respostaAutenticacao));
		response.addHeader(HEADER_STRING,bearerToken);
	}
	
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
}
