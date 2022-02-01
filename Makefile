MIGRATION_LABEL = "to-be-changed"
DATE_WITH_TIME := $(shell /bin/date "+%Y%m%d%H%M%S")
EXCLUDE_OBJECTS = "column:query_token"
PROPERTY_FILE = ""

migration:
	mvn liquibase:diff -Dliquibase.propertyFile="${PROPERTY_FILE}" -Dliquibase.diffExcludeObjects=${EXCLUDE_OBJECTS}
	cp src/main/resources/db/changelog/to-be-changed.yaml src/main/resources/db/changelog/changes/${DATE_WITH_TIME}-${MIGRATION_LABEL}.yaml
	: > src/main/resources/db/changelog/to-be-changed.yaml
	@echo "  - include:" >> src/main/resources/db/changelog/db.changelog-master.yaml
	@echo "      file: db/changelog/changes/$(DATE_WITH_TIME)-$(MIGRATION_LABEL).yaml" >> src/main/resources/db/changelog/db.changelog-master.yaml

rollback:
	mvn liquibase:rollback -Dliquibase.propertyFile="${PROPERTY_FILE}" -Dliquibase.rollbackCount=1
