package com.gemini.ambacam.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Requerant.class)
public abstract class Requerant_ extends com.gemini.ambacam.model.AuditingCommonEntity_ {

	public static volatile SingularAttribute<Requerant, String> profession;
	public static volatile SingularAttribute<Requerant, String> lieuNaissance;
	public static volatile SingularAttribute<Requerant, Pays> nationalite;
	public static volatile SingularAttribute<Requerant, Date> dateNaissance;
	public static volatile SetAttribute<Requerant, Role> roles;
	public static volatile SingularAttribute<Requerant, String> sexe;
	public static volatile SingularAttribute<Requerant, Long> id;
	public static volatile SingularAttribute<Requerant, String> nom;
	public static volatile SingularAttribute<Requerant, String> prenom;
	public static volatile SingularAttribute<Requerant, Operateur> creePar;

}

