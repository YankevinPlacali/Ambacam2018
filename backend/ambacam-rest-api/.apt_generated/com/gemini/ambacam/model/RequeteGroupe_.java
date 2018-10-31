package com.gemini.ambacam.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequeteGroupe.class)
public abstract class RequeteGroupe_ extends com.gemini.ambacam.model.AuditingCommonEntity_ {

	public static volatile SingularAttribute<RequeteGroupe, String> description;
	public static volatile SingularAttribute<RequeteGroupe, Long> id;
	public static volatile SetAttribute<RequeteGroupe, Requete> requetes;
	public static volatile SingularAttribute<RequeteGroupe, String> nom;
	public static volatile SingularAttribute<RequeteGroupe, Operateur> creePar;

}

