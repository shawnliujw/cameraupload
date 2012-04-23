package com.mpupa.filter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.springframework.web.filter.OncePerRequestFilter;


public class SystemFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		filterChain.doFilter(request, response);
		
	}
 
}

