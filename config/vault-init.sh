#!/bin/sh

VAULT_DEV_TOKEN=934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

vault login ${VAULT_DEV_TOKEN}

vault login

vault secrets disable secret
vault secrets enable -version=1 -path=secret kv
vault kv put secret/application @${CONFIG_DIR}/application.json
vault kv put secret/catalog-service @${CONFIG_DIR}/catalog-service.json
vault kv put secret/inventory-service @${CONFIG_DIR}/inventory-service.json
