$SUB_ID="1f1969ba-b3a4-4dec-b674-880f201357ec"
$RESOURCE_GROUP="rg-trading-app-aks"
$ACR_NAME="jrvsregistry"

az acr create `
--resource-group $RESOURCE_GROUP `
--subscription $SUB_ID `
--name $ACR_NAME `
--sku Basic

az aks update `
--name $CLUSTER_NAME `
--resource-group $RESOURCE_GROUP `
--attach-acr $ACR_NAME

cd springboot 
az acr build `
--image trading-app `
--registry $ACR_NAME `
--file Dockerfile .