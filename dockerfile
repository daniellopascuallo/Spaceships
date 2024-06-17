# Usa la imagen oficial de PostgreSQL como base
FROM postgres:latest

# Configura las variables de entorno para la base de datos
ENV POSTGRES_DB spaceships
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD myapptest123

# Copia los scripts de inicialización al contenedor (si los hay)
# Estos scripts se ejecutan en el primer arranque del contenedor
# COPY init.sql /docker-entrypoint-initdb.d/

# Exponer el puerto 5432 para PostgreSQL
EXPOSE 5432

# El comando por defecto para ejecutar PostgreSQL
CMD ["postgres"]
