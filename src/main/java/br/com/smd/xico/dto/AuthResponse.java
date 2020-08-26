package br.com.smd.xico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * RespostaAutenticacao
 */

@AllArgsConstructor
@ToString
@Builder
@Getter
public class AuthResponse {
	private Long id;
	private String token;
	private String role;
	private String username;
}