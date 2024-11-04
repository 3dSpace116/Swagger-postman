
databaseChangeLog:
  - changeSet:
      id: 1
      author: my_name
      changes:
        - createIndex:
            tableName: faculties
            indexName: idx_faculties_name_color
            columns:
              - name: name
              - name: color