README:

Pasos para compilar el proyecto.

paso 1: Guardar los archivos EspolKeyValueStore.class, ClienteEspolKeyValueStore.class y makefile.txt en un directorio.
paso 2: Abrir el shell e ir al directorio donde estan guardados los archivos del paso 1.
paso 3: Usar el comando
		
	make makefile.txt

Para correr el servidor escribir en el shell:
	
	java EspolKeyValueStore [puerto] 

Para correr el clienteen el shell:

	java ClienteEspolKeyValueStore [ip del servidor] [puerto]
