# Mundo Bancario 
#### _Microservicios_

# Configuración envio email
> Para que funcione el envio de correo tendrias que clonarte este repositorio https://github.com/ismael999/config-cloud.git y poner los datos del tu email en al archivo 'aplication.yml' del repositorio, despues en el archivo 'aplication.yml' del servicio 'ConfigServer' tendrias que cambiar la ruta de mi repositorio por la tuya.

# Acceso desde Gateway
> Para acceder a una microservicio especifico tienes que poner la dirección del Gateway (http://localhost:8080) seguido de (/micro-) y el nombre del microservicio al que quieres acceder, por ejemplo: servicio de clientes -> http://localhost:8080/micro-cliente/*.

> Hay 3 microservicios principales, **micro-cliente, micro-cuenta y micro-report**. Los demás microservicios no deverían ser accesibles desde el fron, solo desde otros microservicios.
