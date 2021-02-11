package br.com.zup.proposta.validations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
	
	private Class<?> domainClass;
	private String field;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		this.domainClass = constraintAnnotation.domainClass();
		this.field = constraintAnnotation.field();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Query query = manager.createQuery("SELECT 1 FROM "+this.domainClass.getName()+" d WHERE "+this.field+" = :value");
		query.setParameter("value", value);
		
		List<?> lista = query.getResultList();
		
		return lista.isEmpty();
	}
}