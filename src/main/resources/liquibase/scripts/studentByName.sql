databaseChangeLog:
  - changeSet:
      id: 1
      author: my_name
      changes:
        - createIndex:
            tableName: students
            indexName: idx_students_name
            columns:
              - name: name