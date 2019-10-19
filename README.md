[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![stable](http://badges.github.io/stability-badges/dist/stable.svg)](http://github.com/badges/stability-badges)


# Collection Manger backend (API)

Collection manager is a tool to manager different collection like movies, games, ... This project is the full API of the application. This will only give you a rest api to connect to the logic of Collection Manager. There is also a nice frontend to view and edit the data. The frontend can be found on [Github](https://github.com/lonelobo0070/Collection-Manager-Frontend)

## Requirements
- PHP 7.3
- Apacke HTTP server or equivalent

## Used libraries

- [ReallySimpleJWT](https://github.com/RobDWaller/ReallySimpleJWT)
- [Slim Framework](http://www.slimframework.com/)

## How to build the code

The application contains a dockerfile soe we can build the code by running:

    sudo docker build -t cm_backend .


After building the source code into an image we can create a container:

    sudo docker create -p 8080:8080 cm_backend
