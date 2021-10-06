[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
![Full build](https://github.com/jimmyd-be/Collection-Manager/workflows/Full%20build/badge.svg?branch=master)

# Collection manager

Collection manager is a tool to manage different collections. The tools default support following types: movies, games, books, magazines, comics.
If you want to have a custom collection please submit a issue or PR.
  

### Features
- Customizable themes
- Customizable fields
- Different collection types:
	- Books
	- Comics
	- Games
	- Magazines
	- Movies
	- ....
- Import collection items
	- by external system search
	- Input manually
	- Import from file
- User Management (role based)
	- Admin (can perform all actions on a collection)
	- Editor (can insert, edit and delete items from a collection)
	- Viewer (can only view the collection and his items)

### Quick install with docker-compose

```
#docker-compose.yml
version: "3"

volumes:
    logs:
        driver: local

services:
    db:
        image: postgres:alpine
        restart: always
        environment:
            POSTGRES_PASSWORD: testDatabase
            POSTGRES_DB: newcm
        ports:
            - "5432:5432"
        expose:
            - 5432
    api:
        image: lonelobo0070/collection_manager_backend:[tag]
        depends_on:
            - db
        ports:
            - "8080:8080"
        expose:
            - 8080
    gui:
                image: lonelobo0070/collection_manager_frontend:[tag]
        ports:
            - "8090:80"
        depends_on:
            - api

volumes:
  container-volume:

```

Change the ```[tag]``` to the correct tag that you want to use (develop, latest). After changing you can just run ```docker-compose up -d```. When the application is started you can browse it using a modern browser and browse to ```http://ip-address:8090/```.

### First usage of Collection Manager
When starting Collection Manager for the first time there will be no user in the system. You should register your user to login to it. The first created user will automaticly be an admin user. After registration you can just login with the create credentials.
 

## Developers

### Requirements
- For developers:
	- Node.js verion 10.x or greater
	- Angular CLI
	- Apache HTTPD or equivalents
	- Java 11
	- Maven
	- docker (optional)
	- Mariadb or equivalents
- For users:
	- Html 5 browser
	
### Used libraries

- [Angular]([https://angular.io/](https://angular.io/))
- [Nebular]([https://akveo.github.io/nebular/](https://akveo.github.io/nebular/))
- [Spring](https://spring.io)



### How to build the code (using Docker)

The application contains a script that will build the code and create the needed docker images.

    docker-compose -d up

### How to remove docker stack

    docker-compose down --rmi 'all'

### How to contribute to this project
You can contribute any kind of new features, bugfixes, ... to the project. The features that need to be implemented can be found at the project page of the repository. If some nice feature is missing please contact me and I will add it to the project. For some help to contribute to this project please read the great guide "[First Contributions](https://github.com/firstcontributions/first-contributions)".
