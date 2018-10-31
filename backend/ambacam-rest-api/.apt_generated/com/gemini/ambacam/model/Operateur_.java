package com.gemini.ambacam.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Operateur.class)
public abstract class Operateur_ extends com.gemini.ambacam.model.AuditingCommonEntity_ {

	public static volatile SingularAttribute<Operateur, Pays> nationalite;
	public static volatile SingularAttribute<Operateur, String> password;
	public static volatile SetAttribute<Operateur, Role> roles;
	public static volatile SingularAttribute<Operateur, String> sexe;
	public static volatile SingularAttribute<Operateur, Long> id;
	public static volatile SingularAttribute<Operateur, String> nom;
	public static volatile SingularAttribute<Operateur, String> prenom;
	public static volatile SingularAttribute<Operateur, Operateur> creePar;
	public static volatile SingularAttribute<Operateur, String> username;

}

