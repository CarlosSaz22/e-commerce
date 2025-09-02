#!/bin/bash

function show_menu {
    echo "Selecciona las configuraciones de los servicess a crear (separa con comas para elegir múltiples opciones):"
    echo "1) services Order"
    echo "2) services Inventory"
    echo "3) services Notify"
    echo "4) Monitoreo y Observabilidad"
	  echo "5) Ingress servicess"
    echo "0) Salir"
    echo " "
    read -p "Selecciona una o más opciones (ejemplo: 1,2): " user_selection
    echo " "
}

function services_inventory() {
    echo "Ejecutando k8s para services-inventory"
    echo ":::::::::::::::::::::::::::::::::::::::::::"
    echo " "
    echo "Creando configmap ::::::::::::"
    kubectl apply -f 2-1-configmap-services-inventory.yaml
    echo " "
    sleep 2
    echo "Creando Deploy ::::::::::::"
    kubectl apply -f 2-2-deploy-services-inventory.yaml
    echo " "
    sleep 2
    echo "Creando HPA ::::::::::::"
    kubectl apply -f 2-3-hpa-services-inventory.yaml
    echo " "
    sleep 2
    echo "Creando Services ::::::::::::"
    kubectl apply -f 2-4-services-services-inventory.yaml
    echo " "
}

function services_order() {
    echo "Ejecutando k8s para services-order"
    echo ":::::::::::::::::::::::::::::::::::::::::::"
    echo " "
    echo "Creando secreto ::::::::::::"
    kubectl apply -f 1-1-secrets-services-order.yaml
    echo " "
    sleep 2
    echo "Creando configmap ::::::::::::"
    kubectl apply -f 1-2-configmap-services-order.yaml
    echo " "
    sleep 2
    echo "Creando Deploy ::::::::::::"
    kubectl apply -f 1-3-deploy-services-order.yaml
    echo " "
    sleep 2
    echo "Creando HPA ::::::::::::"
    kubectl apply -f 1-4-hpa-services-order.yaml
    echo " "
    sleep 2
    echo "Creando Services ::::::::::::"
    kubectl apply -f 1-5-services-services-order.yaml
    echo " "
}

function services_notify() {
    echo "Ejecutando k8s para services-notify"
    echo ":::::::::::::::::::::::::::::::::::::::::::"
    echo " "
    echo "Creando configmap ::::::::::::"
    kubectl apply -f 3-1-configmap-services-notify.yaml
    echo " "
    sleep 2
    echo "Creando Deploy ::::::::::::"
    kubectl apply -f 3-2-deploy-services-notify.yaml
    echo " "
    sleep 2
    echo "Creando Services ::::::::::::"
    kubectl apply -f 3-3-services-services-notify.yaml
    echo " "
}

function services_monitoreo_observabilidad() {
    echo "Ejecutando k8s para observabilidad y monitoreo"
    echo ":::::::::::::::::::::::::::::::::::::::::::"
    kubectl create namespace obs-mon
    echo " "
    echo "Creando configmap ::::::::::::"
    kubectl apply -f 4-1-configmap-obser-metric.yaml
    echo " "
    sleep 2
    echo "Creando Deploy ::::::::::::"
    kubectl apply -f 4-2-deploy-obser-metric.yaml
    echo " "
    sleep 2
    echo "Creando Services ::::::::::::"
    kubectl apply -f 4-3-services-obser-metric.yaml
    echo " "
}

function servicess_ingress() {
	echo "Ejecutando k8s Ingress servicess"
	echo ":::::::::::::::::::::::::::::::::::::::::::"
	kubectl apply -f 5-2-ingress-services.yaml
	echo " "
	sleep 2
}

# Bucle del menú
while true; do
    show_menu

    IFS=',' read -r -a selections <<< "$user_selection"

    for selection in "${selections[@]}"; do
        case $selection in
            1)
                services_order
                ;;
            2)
                services_inventory
                ;;
            3)
                services_notify
                ;;
            4)
                services_monitoreo_observabilidad
                ;;
			      5)
                servicess_ingress
                ;;
            0)
                echo "Saliendo..."
                exit 0
                ;;
            *)
                echo "Opción no válida: $selection"
                ;;
        esac
    done

    echo " "
done