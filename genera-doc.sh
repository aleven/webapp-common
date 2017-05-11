mvn site
mvn javadoc:javadoc
rsync -avz --delete target/site/ docs/
