package org.sid.Application.GlobalConfig;

import org.sid.Application.Entities.Pakage;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

public class GlobalConfig extends RepositoryRestConfigurerAdapter{
   
	public void configureRepositoryRestConfiguaration(RepositoryRestConfiguration repositoryRestConfiguration) {
		
		repositoryRestConfiguration.setReturnBodyOnCreate(true);
		repositoryRestConfiguration.setReturnBodyOnUpdate(true);
		repositoryRestConfiguration.exposeIdsFor(Pakage.class);
		repositoryRestConfiguration.getCorsRegistry().addMapping("/**").allowedOrigins("http:/localhost:8080").allowedHeaders("*")
		.allowedMethods("POST","GET","PUT","PATCH","DELETE","HEAD","OPTIONS");
	}
}
