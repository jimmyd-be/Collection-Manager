#!/bin/sh
export DOCKER_BUILDKI=1


docker-compose -f docker-compose-e2e.yml build
docker-compose -f docker-compose-e2e.yml up -d

cd ./gui && npm run e2e && cd ..

docker-compose -f docker-compose-e2e.yml down --rmi 'all'
docker-compose rm -f

