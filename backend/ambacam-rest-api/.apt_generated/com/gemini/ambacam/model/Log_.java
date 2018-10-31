package com.gemini.ambacam.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Log.class)
public abstract class Log_ {

	public static volatile SingularAttribute<Log, Date> date;
	public static volatile SingularAttribute<Log, String> description;
	public static volatile SingularAttribute<Log, Long> id;

}

