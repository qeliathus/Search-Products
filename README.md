# SearchProducts

SearchProducts - это Java-приложение, которое позволяет загружать и искать продукты в Elasticsearch.

## Технологии

- Java 17
- Spring Boot
- PostgreSQL
- Elasticsearch
- Docker
- Flyway
  
### Предварительные требования

Убедитесь, что у вас установлены:

- Git
- Docker
- Docker Compose

### Инструкция

1. Клонируйте репозиторий:
git clone https://github.com/qeliathus/Search-Products
cd <имя_папки_репозитория>

2. Постройте Docker-образ:
docker build -t search-products-app .

3. Запустите Docker Compose:
docker-compose up

4. После запуска всех сервисов, приложение должно быть доступно по адресу:
http://localhost:8080
