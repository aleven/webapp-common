package it.webappcommon.lib.jpa;

public interface JpaEntityWithListeners extends JpaEntity {

	/**
	 * <p>beforeCreate.</p>
	 *
	 * @throws Exception if any.
	 */
	void beforeCreate() throws Exception;

	/**
	 * <p>afterCreate.</p>
	 *
	 * @throws Exception if any.
	 */
	void afterCreate() throws Exception;

	/**
	 * <p>beforeUpdate.</p>
	 *
	 * @throws Exception if any.
	 */
	void beforeUpdate() throws Exception;

	/**
	 * <p>afterUpdate.</p>
	 *
	 * @throws Exception if any.
	 */
	void afterUpdate() throws Exception;

	/**
	 * <p>beforeDelete.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	void beforeDelete() throws Exception;

	/**
	 * <p>afterDelete.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	void afterDelete() throws Exception;

}
