package br.com.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Municipio.class)
public abstract class Municipio_ {

	public static volatile SingularAttribute<Municipio, String> uf;
	public static volatile SingularAttribute<Municipio, BigDecimal> limitevlfrete;
	public static volatile SingularAttribute<Municipio, Integer> distanciasedekm;
	public static volatile SingularAttribute<Municipio, BigDecimal> percentualfrete;
	public static volatile SingularAttribute<Municipio, BigDecimal> vlfrete;
	public static volatile SingularAttribute<Municipio, String> nome;
	public static volatile SingularAttribute<Municipio, Integer> pk;
	public static volatile SingularAttribute<Municipio, Regiao> regiao;
	public static volatile SingularAttribute<Municipio, Integer> ordementrega;

}

