package br.com.zup.proposta.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zup.proposta.util.AESUtil;

public class CPFOuCNPJValidator implements ConstraintValidator<CPFOuCNPJ, String> {
	
	@Autowired
	private AESUtil aesUtil;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		CPFValidator cpfValidator = new CPFValidator();
		cpfValidator.initialize(null);
		
		CNPJValidator cnpjValidator = new CNPJValidator();
		cnpjValidator.initialize(null);
		
		boolean isCPF = cpfValidator.isValid(value, null);
		boolean isCNPJ = cnpjValidator.isValid(value, null);
		
		return (isCPF || isCNPJ);
	}
}
