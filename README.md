# A set of class usefull to build a new WebApp with JSF2 and JPA2, REST, JAXB, JSON, etc.

Charset source UTF-8

## MAVEN DOC and JAVA DOC

- [https://aleven.github.io/webapp-common/](https://aleven.github.io/webapp-common/)
- [https://aleven.github.io/webapp-common/apidocs/](https://aleven.github.io/webapp-common/apidocs/)

https://dzone.com/articles/publish-your-artifacts-to-maven-central

    mvn clean compile javadoc:javadoc
    
    mvn clean
    
    mvn release:prepare
    
    mvn release:perform

git tag -d webapp-common-3.2.14

git push origin :refs/tags/webapp-common-3.2.14

## _it.attocchi.jpa2_

A set of class for work with JPA Entitites. You can find Session or Context Listener to use for lazy close of JPA Controller (EntityManagerFactory lifecicle)

### Example for JPAController usage:

```java
    /* you can use this code on standalone java program, for JSF web-app is better to work with a shared emf */
    List<MyEntity> list = JPAController.callFindPU("PU_NAME", MyEntity.class, myEntityFilter);
```   

### Example for JPAEntityFilter

```java
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
	
		/* build here you filter logic */
		if (StringUtils.isNotBlank(fieldValue)) {
			predicateList.add(criteriaBuilder.equal(root.get(Attivita_.sample), fieldValue));
		}

	}

	@Override
	public void buildSort(CriteriaQuery<Attivita> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<Attivita> root) 	{

		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(MyEntity_.sample)));

	}
```

## _it.attocchi.jsf2_

you can find base class for your BackBean 

## _it.attocchi.utils_

a set of utils

## _it.attocchi.db_

something to work easy with org.apache.commons.dbutils.DbUtils or JDBC