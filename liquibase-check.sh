#!/bin/bash
echo "Checking if liquibase changesets have been applied..."
EXPECTED_CHANGESET_COUNT=13  # Replace with the number of changesets you expect to be applied
export PGPASSWORD='secret'  # Add your password here
APPLIED_CHANGESET_COUNT=$(psql -h localhost -p 5432 -U myuser -d mydatabase -c "SELECT COUNT(*) FROM databasechangelog;" | sed -n 3p)
if [ $APPLIED_CHANGESET_COUNT -eq $EXPECTED_CHANGESET_COUNT ]
then
  echo "Liquibase changesets have been applied successfully."
  exit 0
else
  echo "Liquibase changesets have not yet completed."
  exit 1
fi