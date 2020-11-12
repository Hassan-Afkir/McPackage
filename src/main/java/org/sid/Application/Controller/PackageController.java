package org.sid.Application.Controller;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.sid.Application.Entities.Pakage;
import org.sid.Application.Model.Functions;

import org.sid.Application.Repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class PackageController {
   
	 @Autowired
	 private PackRepository PackageRepository;
	 private Functions functions = new Functions();
	 
	 @PostMapping(value = "/addPackage")
	 public ResponseEntity<Pakage> addPackage(@RequestBody Pakage newPackage) {
		
	    functions.ruleOfDimensions(newPackage);
		Pakage PakageAdded =  PackageRepository.save(newPackage);

	        if (PakageAdded == null)
	            return ResponseEntity.noContent().build();

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(PakageAdded.getIdPacking())
	                .toUri();

	        return ResponseEntity.created(location).build();
	 }
	 @GetMapping(value = "/getPackageById/{id}")
	 public ResponseEntity<Pakage> GetPackageById(@PathVariable long id){
		 
		 Optional<Pakage> pack = PackageRepository.findById(id);
		 if(pack.isPresent())
			 return new ResponseEntity<>(pack.get(), HttpStatus.OK);
		 else
		     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
	 @GetMapping(value = "/getAllPackage")
	 public ResponseEntity<List<Pakage>> getAllPackage() {
		  List<Pakage> pakages = PackageRepository.findAll();
		  if(pakages.isEmpty())
			  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  return new ResponseEntity<>(pakages, HttpStatus.OK);  
	 }
	 @PatchMapping(value = "/updatePackage/{id}")
	 public ResponseEntity<Pakage> updatePackage(@PathVariable long id, @RequestBody Pakage pack){
		 Optional<Pakage> packageOptional = PackageRepository.findById(id);
		 if(!packageOptional.isPresent()) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 Pakage newPackage = packageOptional.get();
		 
		 if(pack.getPackingRef() != null) {
			 newPackage.setPackingRef(pack.getPackingRef());
		 }
		 if(pack.getActive() != null) {
			 newPackage.setActive(pack.getActive());
		 }
		 if(pack.getQuantity() > 0 ) {
			 newPackage.setQuantity(pack.getQuantity());
		 }
		 if(newPackage.getDelete() != null) {
			 newPackage.setDelete(pack.getDelete());
		 }
		 if(pack.getWidth() > 0) {
			 newPackage.setWidth(pack.getWidth());
		 }
		 if(pack.getHeight() > 0) {
			 newPackage.setHeight(pack.getHeight());
		 }
		 if(pack.getDepth() > 0) {
			 newPackage.setDepth(pack.getDepth());
		 }
		 if(pack.getWeight() > 0) {
			 newPackage.setWeight(pack.getWeight());
		 }
		 if(pack.getTapeLength() > 0) {
			 newPackage.setTapeLength(pack.getTapeLength());
		 }
		 if(pack.getTapeNumber() > 0) {
			 newPackage.setTapeNumber(pack.getTapeNumber());
		 }
		 if(pack.getSupplierReference() != null) {
			 newPackage.setSupplierReference(pack.getSupplierReference());
		 }
		 if(pack.getIdSupplier() > 0) {
			 newPackage.setIdSupplier(pack.getIdSupplier());
		 }
		 PackageRepository.save(newPackage);
		 
		 return ResponseEntity.noContent().build();
		 
	 }
	 @DeleteMapping(value = "/deletePackage/{id}")
	 public ResponseEntity<HttpStatus> deletePackage(@PathVariable long id){
		 try {
		      PackageRepository.deleteById(id);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	 }
	 @GetMapping(value = "/getPackingBySupplier/{idSupplier}")
	 public ResponseEntity<List> getPackingBySupplier(@PathVariable long idSupplier) {
		  List<Pakage> pakages = PackageRepository.findByidSupplier(idSupplier);
		  if(!pakages.isEmpty())
			  return new ResponseEntity<>(pakages, HttpStatus.OK);
		  else
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	 }
	 
	 
	
	 
	 
}
