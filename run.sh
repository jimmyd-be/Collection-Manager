cd ./gui && npm install && ng build --prod
cd ../api && mvn clean install -DskipTests=true
cd .. && docker-compose up
