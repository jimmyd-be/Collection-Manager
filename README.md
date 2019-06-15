# Collection Manger backend (API)

## User libraries

- [ReallySimpleJWT](https://github.com/RobDWaller/ReallySimpleJWT)
- [Slim Framework](http://www.slimframework.com/)

Build docker image
sudo docker build -t cm_backend .


create container
sudo docker create -p 8080:8080 cm_backend