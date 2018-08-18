package com.ambacam.service;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceUnauthorizedException;
import com.ambacam.model.Operateur;
import com.ambacam.repository.OperateurRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService {

	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private OperateurRepository operateurRepository;

	public void revoke(String authorization) {

		if (authorization != null) {
			String token = authorization.trim().replace("Bearer ", "");
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
			tokenStore.removeAccessToken(accessToken);
		} else {
			throw new ResourceUnauthorizedException("The bearer token must not be null");
		}

	}

	public Operateur getConnectedOperateur(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal == null) {
			return null;
		} else {
			String username = principal.getName();
			Operateur operateur = operateurRepository.findOneByUsername(username);
			return operateur;
		}
	}

}
