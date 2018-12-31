/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
    This file is part of WebAppCommon.

    WebAppCommon is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WebAppCommon is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with WebAppCommon.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.attocchi.jpa2;

import it.attocchi.utils.ListUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * <p>Abstract JPAEntityFilter class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class JPAEntityFilter<T extends Serializable> implements Serializable {

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(JPAEntityFilter.class.getName());

	/**
	 * per JSF 2.1 non può essere serializato
	 */
	// protected final transient Logger logger =
	// Logger.getLogger(this.getClass().getName());
	// per fare un post-back in JSF 2.1 devo poter riassegnare con getLogger
	// perche' dopo la deserializzazione e' null
	// protected transient Logger logger =
	// Logger.getLogger(this.getClass().getName());

	public enum SortOrder {
		ASC,
		DESC
	}

	private static final long serialVersionUID = 1L;

	private static final String JOLLY_CHAR = "%";
	// EntityManagerFactory em;

	protected boolean emptyFilterEmptyData;

	/*
	 * Not here because some problem in JSF
	 * "registry does not contain entity manager factory"
	 */
	// CriteriaBuilder criteriaBuilder;
	// CriteriaQuery<T> criteriaQuery;
	// Root<T> root;

	/**
	 * <p>isEmptyFilterEmptyData.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEmptyFilterEmptyData() {
		return emptyFilterEmptyData;
	}

	/**
	 * <p>Setter for the field <code>emptyFilterEmptyData</code>.</p>
	 *
	 * @param emptyFilterEmptyData a boolean.
	 */
	public void setEmptyFilterEmptyData(boolean emptyFilterEmptyData) {
		this.emptyFilterEmptyData = emptyFilterEmptyData;
	}

	protected SortOrder sortOrder = SortOrder.ASC;

	/**
	 * <p>Getter for the field <code>sortOrder</code>.</p>
	 *
	 * @return a {@link it.attocchi.jpa2.JPAEntityFilter.SortOrder} object.
	 */
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * <p>Setter for the field <code>sortOrder</code>.</p>
	 *
	 * @param sortOrder a {@link it.attocchi.jpa2.JPAEntityFilter.SortOrder} object.
	 */
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * <p>getCriteria.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @return a {@link javax.persistence.criteria.CriteriaQuery} object.
	 * @throws java.lang.Exception if any.
	 */
	public CriteriaQuery<T> getCriteria(Class<T> clazz, EntityManagerFactory emf) throws Exception {

		// CriteriaBuilder
		CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);

		// criteria.where( builder.equal( personRoot.get( Utente_.eyeColor ),
		// "brown" ) );

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// buildWhere(emf, predicateList, criteriaQuery, criteriaBuilder, root);
		//
		// Predicate[] predicates = new Predicate[predicateList.size()];
		// predicateList.toArray(predicates);

		Predicate[] predicates = getWherePredicates(emf, predicateList, criteriaQuery, criteriaBuilder, root);
		Predicate wherePredicate = criteriaBuilder.and(predicates);
		criteriaQuery.where(wherePredicate);

		// buildSort(criteriaQuery, criteriaBuilder, root);
		List<Order> orders = new ArrayList<Order>();
		buildSort(orders, criteriaQuery, criteriaBuilder, root);
		if (ListUtils.isNotEmpty(orders))
			criteriaQuery.orderBy(orders);

		return criteriaQuery;
	}

	// Predicate wherePredicate;
	/**
	 * <p>getWherePredicate.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @return a {@link javax.persistence.criteria.Predicate} object.
	 * @throws java.lang.Exception if any.
	 */
	public Predicate getWherePredicate(Class<T> clazz, EntityManagerFactory emf) throws Exception {

		// CriteriaBuilder
		CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// return getWherePredicates(emf, predicateList, criteriaQuery,
		// criteriaBuilder, root);

		Predicate[] predicates = getWherePredicates(emf, predicateList, criteriaQuery, criteriaBuilder, root);
		Predicate wherePredicate = criteriaBuilder.and(predicates);

		return wherePredicate;
	}

	/**
	 * <p>getWherePredicates.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param predicateList a {@link java.util.List} object.
	 * @param criteriaQuery a {@link javax.persistence.criteria.CriteriaQuery} object.
	 * @param criteriaBuilder a {@link javax.persistence.criteria.CriteriaBuilder} object.
	 * @param root a {@link javax.persistence.criteria.Root} object.
	 * @return an array of {@link javax.persistence.criteria.Predicate} objects.
	 * @throws java.lang.Exception if any.
	 */
	public Predicate[] getWherePredicates(EntityManagerFactory emf, List<Predicate> predicateList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) throws Exception {

		buildWhere(emf, predicateList, criteriaQuery, criteriaBuilder, root);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);

		return predicates;
	}

	// public Predicate getBuilderWherePredicate(Class<T> clazz,
	// EntityManagerFactory emf) throws Exception {
	// return wherePredicate;
	// }

	/**
	 * <p>buildWhere.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param predicateList a {@link java.util.List} object.
	 * @param criteriaQuery a {@link javax.persistence.criteria.CriteriaQuery} object.
	 * @param criteriaBuilder a {@link javax.persistence.criteria.CriteriaBuilder} object.
	 * @param root a {@link javax.persistence.criteria.Root} object.
	 * @throws java.lang.Exception if any.
	 */
	public abstract void buildWhere(EntityManagerFactory emf, List<Predicate> predicateList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) throws Exception;

	/**
	 * <p>buildSort.</p>
	 *
	 * @param orderList a {@link java.util.List} object.
	 * @param criteriaQuery a {@link javax.persistence.criteria.CriteriaQuery} object.
	 * @param criteriaBuilder a {@link javax.persistence.criteria.CriteriaBuilder} object.
	 * @param root a {@link javax.persistence.criteria.Root} object.
	 * @throws java.lang.Exception if any.
	 */
	public abstract void buildSort(List<Order> orderList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) throws Exception;

	protected String semeRicerca;

	/**
	 * <p>Getter for the field <code>semeRicerca</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSemeRicerca() {
		return semeRicerca;
	}

	/**
	 * <p>Setter for the field <code>semeRicerca</code>.</p>
	 *
	 * @param semeRicerca a {@link java.lang.String} object.
	 */
	public void setSemeRicerca(String semeRicerca) {
		logger.debug(String.format("setSemeRicerca(%s)", semeRicerca));
		this.semeRicerca = semeRicerca;
	}

	/**
	 * <p>setSemeRicercaL.</p>
	 *
	 * @param semeRicerca a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 */
	public JPAEntityFilter<T> setSemeRicercaL(String semeRicerca) {
		setSemeRicerca(semeRicerca);
		return this;
	}

	/**
	 * <p>semeRicerca.</p>
	 *
	 * @param semeRicerca a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 */
	public JPAEntityFilter<T> semeRicerca(String semeRicerca) {
		setSemeRicerca(semeRicerca);
		return this;
	}

	/**
	 * <p>getSemeRicercaForLike.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSemeRicercaForLike() {
		// String res = semeRicerca;
		//
		// if (StringUtils.isNotEmpty(res)) {
		// if (res.startsWith(JOLLY_CHAR) || res.endsWith(JOLLY_CHAR)) {
		// // OK
		// } else {
		// res = JOLLY_CHAR + semeRicerca + JOLLY_CHAR;
		// }
		// }
		//
		// return res;
		return getForLike(semeRicerca);
	}

	/**
	 * <p>getForLike.</p>
	 *
	 * @param aString a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getForLike(String aString) {
		String res = aString;

		if (StringUtils.isNotEmpty(res)) {
			if (res.startsWith(JOLLY_CHAR) || res.endsWith(JOLLY_CHAR)) {
				// OK
			} else {
				res = JOLLY_CHAR + res + JOLLY_CHAR;
			}
		}

		return res;
	}

	protected boolean includiEliminati = false;

	/**
	 * <p>isIncludiEliminati.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isIncludiEliminati() {
		return includiEliminati;
	}

	/**
	 * <p>Setter for the field <code>includiEliminati</code>.</p>
	 *
	 * @param includiEliminati a boolean.
	 */
	public void setIncludiEliminati(boolean includiEliminati) {
		this.includiEliminati = includiEliminati;
	}

	/**
	 * <p>includiEliminati.</p>
	 *
	 * @return a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 */
	public JPAEntityFilter<T> includiEliminati() {
		setIncludiEliminati(true);
		return this;
	}

	// public JPAEntityFilter<T> setIncludiEliminatiL(boolean includiEliminati)
	// {
	// setIncludiEliminati(includiEliminati);
	// return this;
	// }

	/**
	 * <p>buildMultiIds.</p>
	 *
	 * @param longs a {@link java.util.List} object.
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>splitMultiIds.</p>
	 *
	 * @param stringIds a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
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

	/**
	 * <p>Getter for the field <code>pageNumber</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * <p>Setter for the field <code>pageNumber</code>.</p>
	 *
	 * @param pageNumber a int.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * <p>Getter for the field <code>limit</code>.</p>
	 *
	 * @return a int.
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * <p>Setter for the field <code>limit</code>.</p>
	 *
	 * @param limit a int.
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * <p>limit.</p>
	 *
	 * @param limit a int.
	 * @return a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 */
	public JPAEntityFilter<T> limit(int limit) {
		setLimit(limit);
		return this;
	}

	// private Predicate buildLikePredicateForFields(CriteriaBuilder
	// criteriaBuilder, String semeRicercaForLike, Path<String>... fields) {
	//
	// List<Predicate> ors = new ArrayList<Predicate>();
	// for (Path<String> field : fields) {
	// // Predicate p1 = (criteriaBuilder.like(root.get(Attivita_.oggetto),
	// // getSemeRicercaForLike()));
	// // Predicate p2 = (criteriaBuilder.like(root.get(Attivita_.notes),
	// // getSemeRicercaForLike()));
	// ors.add(criteriaBuilder.like(field, semeRicercaForLike));
	// }
	//
	// return criteriaBuilder.or(ListUtils.toArray(aList));
	//
	// }

	/*
	 * FUNZIONI UTILI PER PAGINATORI
	 */
	/**
	 * <p>getCurrentPage.</p>
	 *
	 * @return a int.
	 */
	public int getCurrentPage() {
		return (limit > 0) ? pageNumber + 1 : 1;
	}

	/**
	 * <p>totalPages.</p>
	 *
	 * @param count a long.
	 * @return a int.
	 */
	public int totalPages(long count) {
		return (limit > 0 && count > limit) ? (int) ((count / limit) + 1) : 1;
	}

	/**
	 * <p>buildMultiWordLikePredicate.</p>
	 *
	 * @param criteriaBuilder a {@link javax.persistence.criteria.CriteriaBuilder} object.
	 * @param paths a {@link javax.persistence.criteria.Path} object.
	 * @return a {@link javax.persistence.criteria.Predicate} object.
	 */
	protected Predicate buildMultiWordLikePredicate(CriteriaBuilder criteriaBuilder, Path<String>... paths) {

		String[] words = semeRicerca.split(" ");

		List<Predicate> likeAllFields = new ArrayList<Predicate>();

		for (Path<String> path : paths) {

			List<Predicate> likeOnWord = new ArrayList<Predicate>();
			for (String word : words) {
				likeOnWord.add(criteriaBuilder.like(path, getForLike(word)));
			}
			Predicate p1 = criteriaBuilder.and(likeOnWord.toArray(new Predicate[likeOnWord.size()]));
			likeAllFields.add(p1);

			likeOnWord.clear();
		}

		Predicate res = criteriaBuilder.or(likeAllFields.toArray(new Predicate[likeAllFields.size()]));

		return res;
	}

	// /**
	// * Trovare una soluzione per Logger
	// * @return
	// */
	// @Deprecated
	// private Logger getLogger() {
	// if (logger == null) {
	// logger = Logger.getLogger(this.getClass().getName());
	// }
	// return logger;
	// }

	private boolean filtriAvanzati;

	/**
	 * <p>isFiltriAvanzati.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFiltriAvanzati() {
		return filtriAvanzati;
	}

	/**
	 * <p>Setter for the field <code>filtriAvanzati</code>.</p>
	 *
	 * @param filtriAvanzati a boolean.
	 */
	public void setFiltriAvanzati(boolean filtriAvanzati) {
		this.filtriAvanzati = filtriAvanzati;
	}
}
