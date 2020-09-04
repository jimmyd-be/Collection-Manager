CREATE TABLE cm_user (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL,
	mail varchar(255) NOT NULL,
	userPassword VARCHAR(255) NULL,
    creationDate DATETIME NOT NULL,
    isAdmin BOOL NULL,
    theme VARCHAR(255) NULL,
	active BOOL NULL,
	PRIMARY KEY (id),
	CONSTRAINT user_UN_mail UNIQUE KEY (mail),
	CONSTRAINT user_UN_name UNIQUE KEY (username)
);


CREATE TABLE cm_role (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT role_UN_role UNIQUE KEY (name)
);

CREATE TABLE cm_collectiontype (
    id int NOT NULL AUTO_INCREMENT,
    type varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT collectionType_UN_type UNIQUE KEY (type)
);

CREATE TABLE cm_collection (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    typeId int NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (typeId)  REFERENCES cm_collectiontype(id)
);

CREATE TABLE cm_usercollection (
    userId BIGINT UNSIGNED NOT NULL,
    collectionId BIGINT UNSIGNED NOT NULL,
    roleId int NOT NULL,
    PRIMARY KEY (userId, collectionId, roleId),
    FOREIGN KEY (userId)  REFERENCES cm_user(id),
    FOREIGN KEY (collectionId)  REFERENCES cm_collection(id),
    FOREIGN KEY (roleId)  REFERENCES cm_role(id)
);

CREATE TABLE cm_item (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image varchar(255) NULL,
    author BIGINT UNSIGNED NOT NULL,
    creationDate DATETIME NOT NULL,
    lastModified DATETIME NULL,
    modifiedBy BIGINT UNSIGNED NULL,
    active BOOl NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author)  REFERENCES cm_user(id)
);

CREATE TABLE cm_collectionitem (
    collectionId BIGINT UNSIGNED NOT NULL,
    itemId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (collectionId, itemId),
    FOREIGN KEY (collectionId)  REFERENCES cm_collection(id),
    FOREIGN KEY (itemId)  REFERENCES cm_item(id)
);

CREATE TABLE cm_fieldtype (
    id int NOT NULL AUTO_INCREMENT,
    type varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT fieldType_UN_type UNIQUE KEY (type)
);

CREATE TABLE cm_field (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name varchar(255) NULL,
  type int NOT NULL,
  choises varchar(255) NULL,
  required BOOL NULL,
  placeHolder varchar(255) NULL,
  label varchar(255) NULL,
  otherOptions varchar(255) NULL,
  collectionBaseType int NULL,
  fieldOrder int(11) NOT NULL,
  place varchar(10) NOT NULL,
  multiValues BOOL NULL,
  active BOOL NULL,
  labelPosition varchar(45) NULL,
  widget varchar(45) NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (type)  REFERENCES cm_fieldtype(id),
  FOREIGN KEY (collectionBaseType)  REFERENCES cm_collectiontype(id)
);

CREATE TABLE cm_itemdata (
    itemId BIGINT UNSIGNED NOT NULL,
    fieldId BIGINT UNSIGNED NOT NULL,
    valueCount BIGINT UNSIGNED NOT NULL,
    fieldValue TEXT,
    PRIMARY KEY (itemId, fieldId, valueCount),
    FOREIGN KEY (itemId) REFERENCES cm_item(id),
    FOREIGN KEY (fieldId) REFERENCES cm_field(id)
);

CREATE TABLE cm_collectionfield(
    collectionId BIGINT UNSIGNED NOT NULL,
    fieldId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (collectionId, fieldId),
    FOREIGN KEY (fieldId) REFERENCES cm_field(id),
    FOREIGN KEY (collectionId) REFERENCES cm_collection(id)
);


INSERT INTO cm_collectiontype (`id`, `type`, `active`) VALUES
(1, 'Movies', 1),
(2, 'Games', 1),
(3, 'Magazines', 1),
(4, 'Books', 1),
(5, 'Disks', 1),
(6, 'Comics', 1);


INSERT INTO cm_fieldtype (`id`, `type`, `active`) VALUES 
(1, 'text',1)
,(2, 'url',1)
,(3, 'textarea',1)
,(4, 'date',1)
,(5, 'dropdown',1)
,(6, 'checkbox',1)
,(7, 'radio',1)
,(8, 'email',1)
,(9, 'rate',1)
,(10, 'image',1)
,(11, 'barcode',1),
(12, 'number', 1);

INSERT INTO cm_role (`id`, `name`, `active`) VALUES
(0, 'Owner', 1),
(1, 'Admin', 1),
(2, 'Editor', 1),
(3, 'Viewer', 1) ON DUPLICATE KEY UPDATE id=id;


/* Movie default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 1, 0, 'top', 0, 1, 'hidden', 'default'),
('genre', 1, NULL, 0, 'Genre', 'Genre', NULL, 1, 10, 'main', 1, 1, 'left', 'default'),
('runtime', 12, NULL, 0, '', 'Runtime', NULL, 1, 20, 'main', 0, 1, 'left', 'default'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 1, 30, 'main', 0, 1, 'left', 'default'),
('storyline', 3, NULL, 0, 'Storyline', 'Storyline', NULL, 1, 40, 'main', 0, 1, 'top', 'default'),
('director', 1, NULL, 0, 'Director', 'Director', NULL, 1, 50, 'main', 1, 1, 'top', 'default'),
('writer', 1, NULL, 0, 'Writer', 'Writer', NULL, 1, 60, 'main', 1, 1, 'top', 'default'),
('cast', 1, NULL, 0, 'Cast', 'Cast', NULL, 1, 60, 'main', 1, 1, 'top', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 1, 70, 'left', 0, 1, 'hidden', 'image'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 1, 80, 'left', 0, 1, 'hidden', 'rate');


/* Games default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 2, 0, 'top', 0, 1, 'hidden', 'default'),
('genre', 1, NULL, 0, 'Genre', 'Genre', NULL, 2, 10, 'main', 1, 1, 'left', 'default'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 2, 30, 'main', 0, 1, 'left', 'default'),
('platform', 1, NULL, 0, 'Platform', 'Platform', NULL, 2, 20, 'main', 1, 1, 'left', 'default'),
('publisher', 1, NULL, 0, 'Publisher', 'Publisher', NULL, 2, 30, 'main', 1, 1, 'left', 'default'),
('storyline', 3, NULL, 0, 'Storyline', 'Storyline', NULL, 2, 40, 'main', 0, 1, 'top', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 2, 70, 'left', 0, 1, 'hidden', 'image'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 2, 80, 'left', 0, 1, 'hidden', 'rate');


/* Magazines default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 3, 0, 'top', 0, 1, 'hidden', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 3, 70, 'left', 0, 1, 'hidden', 'image'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 3, 30, 'main', 0, 1, 'left', 'default'),
('content', 3, NULL, 0, 'Content', 'Content', NULL, 3, 40, 'main', 0, 1, 'top', 'default');

/* Books default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 4, 0, 'top', 0, 1, 'hidden', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 4, 70, 'left', 0, 1, 'hidden', 'image'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 4, 30, 'main', 0, 1, 'left', 'default'),
('isbn', 11, NULL, 1, 'ISBN', 'ISBN', NULL, 4, 0, 'main', 0, 1, 'hidden', 'default'),
('writer', 1, NULL, 0, 'Writer', 'Writer', NULL, 4, 30, 'main', 1, 1, 'left', 'default'),
('publisher', 1, NULL, 0, 'Publisher', 'Publisher', NULL, 4, 30, 'main', 1, 1, 'left', 'default'),
('pages', 12, NULL, 0, 'Pages', 'Pages', NULL, 4, 50, 'main', 0, 1, 'top', 'default'),
('storyline', 3, NULL, 0, 'Storyline', 'Storyline', NULL, 4, 40, 'main', 0, 1, 'top', 'default'),
('genre', 1, NULL, 0, 'Genre', 'Genre', NULL, 4, 10, 'main', 1, 1, 'left', 'default'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 4, 80, 'left', 0, 1, 'hidden', 'rate');


/* Disks default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 5, 0, 'top', 0, 1, 'hidden', 'default'),
('artist', 1, NULL, 0, 'Artist', 'Artist', NULL, 5, 30, 'main', 1, 1, 'left', 'default'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 5, 30, 'main', 0, 1, 'left', 'default'),
('song', 1, NULL, 0, 'Song', 'Song', NULL, 5, 100, 'main', 1, 1, 'left', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 5, 70, 'left', 0, 1, 'hidden', 'image');


/* Comics default fields */
INSERT INTO cm_field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 6, 0, 'top', 0, 1, 'hidden', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 6, 70, 'left', 0, 1, 'hidden', 'image'),
('isbn', 11, NULL, 1, 'ISBN', 'ISBN', NULL, 6, 0, 'main', 0, 1, 'hidden', 'default'),
('writer', 1, NULL, 0, 'Writer', 'Writer', NULL, 6, 30, 'main', 1, 1, 'left', 'default'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 6, 30, 'main', 0, 1, 'left', 'default'),
('publisher', 1, NULL, 0, 'Publisher', 'Publisher', NULL, 6, 30, 'main', 1, 1, 'left', 'default'),
('pages', 12, NULL, 0, 'Pages', 'Pages', NULL, 6, 50, 'main', 0, 1, 'top', 'default'),
('storyline', 3, NULL, 0, 'Storyline', 'Storyline', NULL, 6, 40, 'main', 0, 1, 'top', 'default'),
('genre', 1, NULL, 0, 'Genre', 'Genre', NULL, 6, 10, 'main', 1, 1, 'left', 'default'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 6, 80, 'left', 0, 1, 'hidden', 'rate');
