#!/bin/sh

#VAULT_DEV_TOKEN=s.U0aDBRogyAJPMwMmKHz0nXA7

#vault login ${VAULT_DEV_TOKEN}

vault login

vault secrets disable secret
vault secrets enable -version=1 -path=secret kv
vault kv put secret/application @application.json
vault kv put secret/catalog-service @catalog-service.json





