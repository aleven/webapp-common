/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.lib.jpa2;

import java.io.Serializable;

/**
 * 
 * @author Mirco
 */
public class GenericJpaController<T extends EntityWithId> implements Serializable {

	// // private Class<T> aClass;
	// // private T type;
	// final private Class<T> returnedClass;
	//
	// public Class getGenericType() {
	//
	// return returnedClass;
	//
	// // return type.getClass();
	// // return ((ParameterizedType)
	// //
	// getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass();
	// // return getClass().get
	// }
	//
	// /**
	// * Questo tipo di costruttore e' necessario perche' al momento e'
	// * impossibile risalire al tipo dal parametro Generics
	// *
	// * @param clazz
	// */
	// public GenericJpaController(Class<T> clazz) {
	// returnedClass = clazz;
	// }
	//
	// // private Type type;
	// //
	// // private void TypeRef() {
	// // ParameterizedType superclass = (ParameterizedType)
	// // getClass().getGenericSuperclass();
	// // type = superclass.getActualTypeArguments()[0];
	// // }
	//
	// public GenericJpaController(Class<T> clazz, EntityManagerFactory emf) {
	// this.emf = emf;
	// this.returnedClass = clazz;
	// }
	//
	// private EntityManagerFactory emf = null;
	//
	// public EntityManager getEntityManager() {
	// return emf.createEntityManager();
	// }
	//
	// public void create(T o) {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// em.persist(o);
	// em.getTransaction().commit();
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public void edit(T o) throws NonexistentEntityException, Exception {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// o = em.merge(o);
	// em.getTransaction().commit();
	// } catch (Exception ex) {
	// String msg = ex.getLocalizedMessage();
	// if (msg == null || msg.length() == 0) {
	// Integer id = o.getId();
	// if (findEntity(id) == null) {
	// throw new NonexistentEntityException("The Entity with id " + id +
	// " no longer exists.");
	// }
	// }
	// throw ex;
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public void destroy(Integer id) throws NonexistentEntityException {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// T o;
	// try {
	// o = (T) em.getReference(getGenericType(), id);
	// o.getId();
	// } catch (EntityNotFoundException enfe) {
	// throw new NonexistentEntityException("The Entity with id " + id +
	// " no longer exists.", enfe);
	// }
	// em.remove(o);
	// em.getTransaction().commit();
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public List<T> findEntityEntities() {
	// return findEntityEntities(true, -1, -1);
	// }
	//
	// public List<T> findEntityEntities(int maxResults, int firstResult) {
	// return findEntityEntities(false, maxResults, firstResult);
	// }
	//
	// private List<T> findEntityEntities(boolean all, int maxResults, int
	// firstResult) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	// cq.select(cq.from(getGenericType()));
	// Query q = em.createQuery(cq);
	// if (!all) {
	// q.setMaxResults(maxResults);
	// q.setFirstResult(firstResult);
	// }
	// return q.getResultList();
	// } finally {
	// em.close();
	// }
	// }
	//
	// public List<T> findEntityEntities(String semeRicerca, boolean all, int
	// maxResults, int firstResult) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	//
	// javax.persistence.criteria.CriteriaBuilder cb =
	// getEntityManager().getCriteriaBuilder();
	//
	// Root<T> rt = cq.from(getGenericType());
	//
	// cq.select(rt);
	// // cq.where(Restrictions.like("ragioneSociale1", "%" + semeRicerca +
	// // "%"))
	// if (semeRicerca != null && !semeRicerca.isEmpty()) {
	// // cq.where(cb.like(rt.get(Entity_.ragioneSociale1), "%"
	// // + semeRicerca + "%"));
	// }
	//
	// Query q = em.createQuery(cq);
	// if (!all) {
	// q.setMaxResults(maxResults);
	// q.setFirstResult(firstResult);
	// }
	// return q.getResultList();
	// } finally {
	// em.close();
	// }
	// }
	//
	// public T findEntity(Integer id) {
	// EntityManager em = getEntityManager();
	// try {
	// return (T) em.find(getGenericType(), id);
	// } finally {
	// em.close();
	// }
	// }
	//
	// public int getEntityCount(String search) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	// Root<T> rt = cq.from(getGenericType());
	// cq.select(em.getCriteriaBuilder().count(rt));
	//
	// if (search != null && !search.isEmpty()) {
	// //
	// cq.where(getEntityManager().getCriteriaBuilder().like(rt.get(Entity_.),
	// // "%" + search + "%"));
	// }
	//
	// Query q = em.createQuery(cq);
	// return ((Long) q.getSingleResult()).intValue();
	// } finally {
	// em.close();
	// }
	// }
}
