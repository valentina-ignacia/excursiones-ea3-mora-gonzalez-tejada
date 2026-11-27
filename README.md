# Trabajo Colaborativo Excursiones - EA3

# 1. Integrantes

- Valentina Mora
- Javiera Gonzalez
- Liset Tejada

# 2. Motor de base de datos utilizado

- MySQL
- 
# 3. Pasos de la creación de la DB  y ejecución de la aplicación

- Ejecutar MySQL Workbench
- Ejecutar script "excursiones_mysql.sql"
- Comprobar la creación de la base de datos.
- Abrir IntelliJ
- Configurar JDK 21
- Configurar credenciales para la conexión
- Ejecutar Launcher.java

# 4. Distribución del trabajo

- main: Valentina Mora - Responsable de creación del repositorio y subir el proyecto base
- feature/conexion: Liset Tejada - Responsable de la conexión JDBC 
- feature/lectura-insercion: Javiera Gonzalez - Responsable de la implementación ExcursionDAO a MySQLExcursionDAO edición de las query y creación métodos lectura e inserción. También responsable del desarrollo de "ExcursionMapper"
- feature/actualizacion-eliminacion: Valentina Mora - Responsable de la implementación de actualizar y eliminar
- feature/integracion: Liset Tejada - Responsable de asignación del DAO en MainController, verificación con los datos de prueba
- archivo README.md: Valentina Mora - creación del archivo README.md