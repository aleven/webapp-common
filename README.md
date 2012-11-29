#A set o class usefull to build a new WebApp with JSF2 and JPA2.

##_it.attocchi.jpa2_

###Example for JPAController usage:

    List<MyEntity> list = JPAController.callFindPU("PU_NAME", MyEntity.class, myEntityFilter);

###Example for JPAEntityFilter

public class MyEntityFilter extends JPAEntityFilter<MyEntity> {

	private String fieldValue;

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void buildWhere(EntityManagerFactory emf, List<Predicate> predicateList, CriteriaQuery<Attivita> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<Attivita> root) {

		if (StringUtils.isNotBlank(fieldValue)) {
			predicateList.add(criteriaBuilder.equal(root.get(Attivita_.sample), fieldValue));
		}

	}

	@Override
	public void buildSort(CriteriaQuery<Attivita> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<Attivita> root) {

		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(MyEntity_.sample)));

	}

#you can find Session or Context Listener to use for lazy close of JPA Controller.

##_it.attocchi.jsf2_

#you can find base class for your BackBean 

##_it.attocchi.utils_

#a set of utils

##_it.attocchi.db_

#something to work easy with org.apache.commons.dbutils.DbUtils or JDBC