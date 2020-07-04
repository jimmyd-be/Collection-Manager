CREATE TABLE user (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL,
	mail varchar(255) NOT NULL,
	userPassword VARCHAR(255) NULL,
    creationDate DATETIME NOT NULL,
    isAdmin BOOL NULL,
	active BOOL NULL,
	PRIMARY KEY (id),
	CONSTRAINT user_UN_mail UNIQUE KEY (mail),
	CONSTRAINT user_UN_name UNIQUE KEY (username)
);


CREATE TABLE role (
    id int NOT NULL AUTO_INCREMENT,
    role varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT role_UN_role UNIQUE KEY (role)
);

CREATE TABLE collectiontype (
    id int NOT NULL AUTO_INCREMENT,
    type varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT collectionType_UN_type UNIQUE KEY (type)
);

CREATE TABLE collection (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    typeId int NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (typeId)  REFERENCES collectiontype(id)
);

CREATE TABLE usercollection (
    userId BIGINT UNSIGNED NOT NULL,
    collectionId BIGINT UNSIGNED NOT NULL,
    roleId int NOT NULL,
    PRIMARY KEY (userId, collectionId, roleId),
    FOREIGN KEY (userId)  REFERENCES user(id),
    FOREIGN KEY (collectionId)  REFERENCES collection(id),
    FOREIGN KEY (roleId)  REFERENCES role(id)
);

CREATE TABLE item (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image varchar(255) NULL,
    author BIGINT UNSIGNED NOT NULL,
    creationDate DATETIME NOT NULL,
    lastModified DATETIME NULL,
    modifiedBy BIGINT UNSIGNED NULL,
    active BOOl NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author)  REFERENCES user(id)
);

CREATE TABLE collectionitem (
    collectionId BIGINT UNSIGNED NOT NULL,
    itemId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (collectionId, itemId),
    FOREIGN KEY (collectionId)  REFERENCES collection(id),
    FOREIGN KEY (itemId)  REFERENCES item(id)
);

CREATE TABLE fieldtype (
    id int NOT NULL AUTO_INCREMENT,
    type varchar(255) NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT fieldType_UN_type UNIQUE KEY (type)
);

CREATE TABLE field (
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
  FOREIGN KEY (type)  REFERENCES fieldtype(id),
  FOREIGN KEY (collectionBaseType)  REFERENCES collectiontype(id)
);

CREATE TABLE itemdata (
    itemId BIGINT UNSIGNED NOT NULL,
    fieldId BIGINT UNSIGNED NOT NULL,
    valueCount BIGINT UNSIGNED NOT NULL,
    fieldValue TEXT,
    PRIMARY KEY (itemId, fieldId, valueCount),
    FOREIGN KEY (itemId) REFERENCES item(id),
    FOREIGN KEY (fieldId) REFERENCES field(id)
);

CREATE TABLE collectionfield(
    collectionId BIGINT UNSIGNED NOT NULL,
    fieldId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (collectionId, fieldId),
    FOREIGN KEY (fieldId) REFERENCES field(id),
    FOREIGN KEY (collectionId) REFERENCES collection(id)
);


INSERT INTO collectiontype (`id`, `type`, `active`) VALUES
(1, 'Movies', 1),
(2, 'Games', 1);


INSERT INTO fieldtype (id, `type`,active) VALUES 
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

INSERT INTO newcm.`role` (id, role, active) VALUES
(0, 'Owner', 1),
(1, 'Admin', 1),
(2, 'Editor', 1),
(3, 'Viewer', 1);



INSERT INTO field
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
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 1, 70, 'left', 1, 0, 'hidden', 'image'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 1, 80, 'left', 1, 0, 'hidden', 'rate');
