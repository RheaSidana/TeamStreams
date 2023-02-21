# Frame-per-Second-backend

Commands to create Database

+ brew install postgres
+ brew services start postgresql
+ psql postgres (to enter postgres db)
+ \du (to check list of databases)
+ \l (to check list of relations)
+ CREATE USER postgres WITH PASSWORD 'postgres'; (if user postgres does not exist)
+ CREATE DATABASE ts WITH OWNER postgres ENCODING='UTF8' ; (to create database ts with postgres as owner)


use entity name for writing query in repository
