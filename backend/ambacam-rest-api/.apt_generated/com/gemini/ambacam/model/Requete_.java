package com.gemini.ambacam.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Requete.class)
public abstract class Requete_ extends com.gemini.ambacam.model.AuditingCommonEntity_ {

	public static volatile SingularAttribute<Requete, RequeteGroupe> requeteGroupe;
	public static volatile SingularAttribute<Requete, Requerant> requerant;
	public static volatile SingularAttribute<Requete, Long> id;
	public static volatile SingularAttribute<Requete, Operateur> operateur;
	public static volatile SingularAttribute<Requete, TypeRequete> type;
	public static volatile SingularAttribute<Requete, StatusRequete> status;

}

