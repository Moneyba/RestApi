-- Deploy fresh database tables
\i '/docker-entrypoint-initdb.d/tables/schema.sql'
\i '/docker-entrypoint-initdb.d/tables/data.sql'