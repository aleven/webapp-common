package it.espressoft.jpa2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

public abstract class JPAEntityFilter<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String JOLLY_CHAR = "%";
	EntityManagerFactory em;
	
	/*
	 * Not here because some problem in JSF "registry does not contain entity manager factory"
	 */
//	CriteriaBuilder criteriaBuilder;
//	CriteriaQuery<T> criteriaQuery;
//	Root<T> root;

	public CriteriaQuery<T> getCriteria(Class<T> clazz, EntityManagerFactory emf) {

		// CriteriaBuilder
		CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);

		// criteria.where( builder.equal( personRoot.get( Utente_.eyeColor ),
		// "brown" ) );

		List<Predicate> predicateList = new ArrayList<Predicate>();

		buildWhere(predicateList, criteriaQuery, criteriaBuilder, root);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		criteriaQuery.where(criteriaBuilder.and(predicates));

		buildSort(criteriaQuery, criteriaBuilder, root);

		return criteriaQuery;
	}

	public abstract void buildWhere(List<Predicate> predicateList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root);

	public abstract void buildSort(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root);

	protected String semeRicerca;

	public String getSemeRicerca() {
		return semeRicerca;
	}

	public void setSemeRicerca(String semeRicerca) {
		this.semeRicerca = semeRicerca;
	}

	public JPAEntityFilter<T> setSemeRicercaL(String semeRicerca) {
		setSemeRicerca(semeRicerca);
		return this;
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

	protected boolean includiEliminati = false;

	public boolean isIncludiEliminati() {
		return includiEliminati;
	}

	public void setIncludiEliminati(boolean includiEliminati) {
		this.includiEliminati = includiEliminati;
	}

	public JPAEntityFilter<T> setIncludiEliminatiL(boolean includiEliminati) {
		setIncludiEliminati(includiEliminati);
		return this;
	}
}
