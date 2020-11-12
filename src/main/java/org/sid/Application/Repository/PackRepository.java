package org.sid.Application.Repository;

import java.util.List;

import javax.swing.text.html.Option;

import org.sid.Application.Entities.Pakage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;



@RepositoryRestResource
public interface PackRepository extends JpaRepository<Pakage, Long>{
	
      public List<Pakage> findByidSupplier(long idSupplier);
}
