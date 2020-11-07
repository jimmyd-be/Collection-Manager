[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![stable](http://badges.github.io/stability-badges/dist/stable.svg)](http://github.com/badges/stability-badges)

# Collection manager

Collection manager is a tool to manager different collection like movies, games, ...
  

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
	- Node.js verion 10.x or greater
	- Angular CLI
	- Apache HTTPD or equivalents
	- PHP 7.4 or greater
- For users:
	- Html 5 browser
	
## Used libraries

- [Angular]([https://angular.io/](https://angular.io/))
- [Nebular]([https://akveo.github.io/nebular/](https://akveo.github.io/nebular/))
- [ReallySimpleJWT](https://github.com/RobDWaller/ReallySimpleJWT)
- [Slim Framework](http://www.slimframework.com/)


## How to build the code (using Docker)

The application contains a script that will build the Angular code and create the needed docker files.

    sudo sh ./run.sh


 ## How to contribute to this project
You can contribute any kind of new features, bugfixes, ... to the project. The features that need to be implemented can be found at the project page of the repository. If some nice feature is missing please contact me and I will add it to the project. For some help to contribute to this project please read the great guide "[First Contributions](https://github.com/firstcontributions/first-contributions)".
