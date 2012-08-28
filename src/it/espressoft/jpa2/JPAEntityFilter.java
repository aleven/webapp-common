package it.espressoft.jpa2;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class JPAEntityFilter<T extends Serializable> {

	EntityManagerFactory em;
	CriteriaBuilder criteriaBuilder;
	CriteriaQuery<T> criteriaQuery;
	Root<T> root;

	public CriteriaQuery<T> getCriteria(Class<T> clazz, EntityManagerFactory em) {

		// CriteriaBuilder 
		criteriaBuilder = em.getCriteriaBuilder();

		criteriaQuery = criteriaBuilder.createQuery(clazz);
		root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);

		// criteria.where( builder.equal( personRoot.get( Utente_.eyeColor ),
		// "brown" ) );

		buildWhere(criteriaQuery, criteriaBuilder, root);
		buildSort(criteriaQuery, criteriaBuilder, root);

		return criteriaQuery;
	}

	public abstract void buildWhere(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root);
	
	public abstract void buildSort(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root);
}
