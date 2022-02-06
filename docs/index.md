## Collection Manager

Are you tierd to lookup all your collection items manually and loos the items you rent to a friend. That's oldschool and Collction Manager can help you manager all your collections. This from Movies, Games, Books, Comics and easy to add more.

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
  - by external system search (IMDB, GamesDb, ...)
  - Input manually
  - Import from file (in future release)
- User Management (role based)
  - Admin (can perform all actions on a collection)
  - Editor (can insert, edit and delete items from a collection)
  - Viewer (can only view the collection and his items)

### Some screenshots of the application
![Collection view](https://github.com/jimmyd-be/Collection-Manager/blob/master/docs/images/Collection_view_page.png)
![Collection view Dark](https://github.com/jimmyd-be/Collection-Manager/blob/master/docs/images/collection_view_dark_page.png)
![Login page](https://github.com/jimmyd-be/Collection-Manager/blob/master/docs/images/login_page.png)
![Register page](https://github.com/jimmyd-be/Collection-Manager/blob/master/docs/images/registration_page.png)
![Global Search](https://github.com/jimmyd-be/Collection-Manager/blob/master/docs/images/Global_search_page.png)


### How to install

#### Requirements
You can install Collection Manager on every operating system where Docker is supported:
- Linux (including Raspberry Pi)
- Mac Os X
- Windows

You need to install Docker before you can proceed with the installation of Collection Manager. More info on how to install Docker on your system can be found on the [Docker website](https://docs.docker.com/get-docker/).

Also you need to install docker-compose if you are not alreayd have it installed. How to install it on your operating system can be found on the [Docker compose website](https://docs.docker.com/compose/install/).

#### Install Collection Manager
- Clone the git repo or [download docker-compose.yml](https://github.com/jimmyd-be/Collection-Manager/blob/master/docker-compose.yml).
- Open a terminal window (currently the only way to install Collection Manager)
- Go to the docker-compose.yml file on your system
- run 'docker-compose up -d' (on *nix systems it is sometime required to run this in sudo)
- Now it should download all the required files and if everything is ok you can browse to http://<ip-adress>:8090
