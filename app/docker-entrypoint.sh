#!/bin/sh
set -e

echo "Aguardando MySQL em $DB_HOST:$DB_PORT..."

until nc -z -v -w30 "$DB_HOST" "$DB_PORT"; do
  echo "Ainda esperando MySQL em $DB_HOST:$DB_PORT..."
  sleep 3
done

echo "MySQL está disponível. Iniciando aplicação..."

exec java -jar app.jar
