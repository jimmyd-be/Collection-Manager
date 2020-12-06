cd gui && npm install && ng build --prod
cd api && mvn clean install -DskipTests=true
docker-compose up
