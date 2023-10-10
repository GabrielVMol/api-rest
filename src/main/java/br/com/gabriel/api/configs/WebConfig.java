package br.com.gabriel.api.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.gabriel.api.serialization.converter.YamlJackson2HttpMesageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
	
	@Value("${corsOriginPatterns:default}")
	private String corsOriginPatterns = "";
	
	@Override 
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) { // Converter para X-YAML
		converters.add(new YamlJackson2HttpMesageConverter());
	}
	
	@Override 
	public void addCorsMappings(CorsRegistry registry) { // Adicionando o Cors Mappings
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**")
		//.allowedMethods("GET", "POST", "PUT") // Por verbos
		.allowedMethods("*") // Permitir todos
		.allowedOriginPatterns(allowedOrigins)
		.allowCredentials(true);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) { // Configuração para JSON, XML, X-YAML
		// https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		// Via EXTENSION. http://localhost:8080/api/person/v1.xml DEPRECATED on SpringBoot 2.6
		
		// Via QUERY PARAM. http://localhost:8080/api/person/v1?mediaType=xml
		/*
		configurer.favorParameter(true)
			.parameterName("mediaType").ignoreAcceptHeader(true)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML);
		*/
		
		// Via HEADER PARAM. http://localhost:8080/api/person/v1
		
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)
			;
	}

}