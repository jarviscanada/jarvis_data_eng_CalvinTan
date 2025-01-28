az account list
$SUB_ID="1f1969ba-b3a4-4dec-b674-880f201357ec"
$RESOURCE_GROUP="rg-trading-app-aks"
$CLUSTER_NAME="trading-app-aks"
$REGION="eastus"


az group create --name $RESOURCE_GROUP --location eastus --subscription $SUB_ID

$CLUSTER_SIZE=1
$VM_SIZE="Standard_B2s"
$K8S_VERSION="1.30"
az aks create `
--resource-group $RESOURCE_GROUP `
--name $CLUSTER_NAME `
--node-count $CLUSTER_SIZE `
--enable-addons http_application_routing `
--dns-name-prefix jrvs-kubernetes `
--generate-ssh-keys `
--kubernetes-version $K8S_VERSION `
--subscription $SUB_ID `
--node-vm-size $VM_SIZE

az aks get-credentials --name $CLUSTER_NAME --resource-group $RESOURCE_GROUP