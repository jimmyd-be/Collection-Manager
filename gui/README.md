[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![stable](http://badges.github.io/stability-badges/dist/stable.svg)](http://github.com/badges/stability-badges)

# Collection manager Gui

Collection manager Gui is the frontend of a full stack application that can manage your collections of different types. The project is written in [Angular 7](https://angular.io/) and [Nebular](https://akveo.github.io/nebular/).

The backend of this project can be found on [GitHub](https://github.com/lonelobo0070/Collection-Manager-Backend).
  

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

 

### Requirements
- For developers:
	- Node.js verion 8.x or greater
	- Angular CLI
	- Apache HTTPD or equivalents
- For users:
	- Html 5 browser
	
## Used libraries

- [Angular]([https://angular.io/](https://angular.io/))
- [Nebular]([https://akveo.github.io/nebular/](https://akveo.github.io/nebular/))


### Build docker image
	sudo docker build -t cm_frontend .


create container

	sudo docker create -p 80:80 cm_frontend


 ## How to contribute to this project
You can contribute any kind of new features, bugfixes, ... to the project. The features that need to be implemented can be found at the project page of the repository. If some nice feature is missing please contact me and I will add it to the project. For some help to contribute to this project please read the great guide "[First Contributions](https://github.com/firstcontributions/first-contributions)".
