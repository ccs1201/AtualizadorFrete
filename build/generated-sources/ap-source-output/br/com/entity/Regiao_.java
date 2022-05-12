package br.com.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Regiao.class)
public abstract class Regiao_ {

	public static volatile ListAttribute<Regiao, Municipio> municipios;
	public static volatile SingularAttribute<Regiao, String> nome;
	public static volatile SingularAttribute<Regiao, Integer> pk;

}

