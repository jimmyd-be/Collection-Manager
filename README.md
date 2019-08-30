  

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
	


### Build docker image
	sudo docker build -t cm_frontend .


create container

	sudo docker create -p 80:80 cm_frontend


 ### License
 MIT License

Copyright (c) 2019 Jimmy Dom

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.