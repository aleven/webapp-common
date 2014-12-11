package it.attocchi.jpa2.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EntityMarks.class)
public abstract class EntityMarks_ {

	public static volatile SingularAttribute<EntityMarks, Long> utenteModificaId;
	public static volatile SingularAttribute<EntityMarks, Long> utenteCreazioneId;
	public static volatile SingularAttribute<EntityMarks, Date> dataModifica;
	public static volatile SingularAttribute<EntityMarks, Long> utenteCancellazioneId;
	public static volatile SingularAttribute<EntityMarks, Date> dataCancellazione;
	public static volatile SingularAttribute<EntityMarks, Date> dataCreazione;

}

