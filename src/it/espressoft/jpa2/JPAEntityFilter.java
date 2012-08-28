package it.espressoft.jpa2;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

public abstract class JPAEntityFilter<T extends Serializable> {

	private static final String JOLLY_CHAR = "%";
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

	private String semeRicerca;

	public String getSemeRicerca() {
		return semeRicerca;
	}

	public void setSemeRicerca(String semeRicerca) {
		this.semeRicerca = semeRicerca;
	}

	/**
	 * 
	 * @param semeRicerca
	 */
	public String getSemeRicercaForLike() {
		String res = semeRicerca;

		if (StringUtils.isNotEmpty(res)) {
			if (res.startsWith(JOLLY_CHAR) || res.endsWith(JOLLY_CHAR)) {
				// OK
			} else {
				res = JOLLY_CHAR + semeRicerca + JOLLY_CHAR;
			}
		}

		return res;
	}
}
