package it.attocchi.jpa2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public abstract class JPAEntityFilter<T extends Serializable> implements Serializable {

	protected static final Logger logger = Logger.getLogger(JPAEntityFilter.class.getName());

	private static final long serialVersionUID = 1L;

	private static final String JOLLY_CHAR = "%";
	EntityManagerFactory em;

	protected boolean emptyFilterEmptyData;

	/*
	 * Not here because some problem in JSF
	 * "registry does not contain entity manager factory"
	 */
	// CriteriaBuilder criteriaBuilder;
	// CriteriaQuery<T> criteriaQuery;
	// Root<T> root;

	public boolean isEmptyFilterEmptyData() {
		return emptyFilterEmptyData;
	}

	public void setEmptyFilterEmptyData(boolean emptyFilterEmptyData) {
		this.emptyFilterEmptyData = emptyFilterEmptyData;
	}

	Predicate wherePredicate;

	public CriteriaQuery<T> getCriteria(Class<T> clazz, EntityManagerFactory emf) throws Exception {

		// CriteriaBuilder
		CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);

		// criteria.where( builder.equal( personRoot.get( Utente_.eyeColor ),
		// "brown" ) );

		List<Predicate> predicateList = new ArrayList<Predicate>();

		buildWhere(emf, predicateList, criteriaQuery, criteriaBuilder, root);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);

		wherePredicate = criteriaBuilder.and(predicates);

		criteriaQuery.where(wherePredicate);

		buildSort(criteriaQuery, criteriaBuilder, root);

		return criteriaQuery;
	}

	public Predicate getWherePredicate() {
		return wherePredicate;
	}

	public abstract void buildWhere(EntityManagerFactory emf, List<Predicate> predicateList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) throws Exception;

	public abstract void buildSort(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) throws Exception;

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

	protected String buildMultiIds(List<Long> longs) {
		StringBuilder res = new StringBuilder();

		for (Long l : longs) {
			if (res.length() > 0) {
				res.append(",#" + l + "#");
			} else {
				res.append("#" + l + "#");
			}
		}

		return res.toString();
	}

	protected List<Long> splitMultiIds(String stringIds) {
		List<Long> res = new ArrayList<Long>();

		if (stringIds.startsWith("[")) {
			stringIds = stringIds.substring(1);
		}
		if (stringIds.endsWith("]")) {
			stringIds = stringIds.substring(0, stringIds.length() - 1);
		}

		String[] ids = stringIds.split(",");
		if (ids != null && ids.length > 0) {
			for (String stringId : ids) {
				stringId = stringId.trim();
				if (stringId.startsWith("#") && stringId.endsWith("#")) {
					String idToParse = stringId.substring(1, stringId.length() - 1);
					Long id = 0l;

					id = Long.parseLong(idToParse);

					if (id != null && id > 0) {
						res.add(id);
					} else {
						logger.warn("Perhaps Unparsable " + stringIds);
					}
				}
			}
		}

		return res;
	}

	private int pageNumber;
	private int limit;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}


//	private Predicate buildLikePredicateForFields(CriteriaBuilder criteriaBuilder, String semeRicercaForLike, Path<String>... fields) {
//
//		List<Predicate> ors = new ArrayList<Predicate>();
//		for (Path<String> field : fields) {
//			// Predicate p1 = (criteriaBuilder.like(root.get(Attivita_.oggetto),
//			// getSemeRicercaForLike()));
//			// Predicate p2 = (criteriaBuilder.like(root.get(Attivita_.notes),
//			// getSemeRicercaForLike()));
//			ors.add(criteriaBuilder.like(field, semeRicercaForLike));
//		}
//
//		return criteriaBuilder.or(ListUtils.toArray(aList));
//
//	}
}
