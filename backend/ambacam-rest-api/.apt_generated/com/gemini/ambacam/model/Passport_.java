package com.gemini.ambacam.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Passport.class)
public abstract class Passport_ {

	public static volatile SingularAttribute<Passport, String> urlPhoto;
	public static volatile SingularAttribute<Passport, Requete> requete;
	public static volatile SingularAttribute<Passport, String> numero;
	public static volatile SingularAttribute<Passport, Date> dateExpiration;
	public static volatile SingularAttribute<Passport, Date> dateDelivrance;
	public static volatile SingularAttribute<Passport, Autorite> delivrePar;
	public static volatile SingularAttribute<Passport, Long> id;
	public static volatile SingularAttribute<Passport, String> lieuDelivrance;

}

