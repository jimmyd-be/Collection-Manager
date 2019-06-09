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

CREATE TABLE collectionType (
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
    owner BIGINT UNSIGNED NOT NULL,
    active BOOL NULL,
    PRIMARY KEY (id),
    CONSTRAINT collection_UN_name_owner UNIQUE KEY (name, owner),
    FOREIGN KEY (owner)  REFERENCES `user`(id),
    FOREIGN KEY (typeId)  REFERENCES collectionType(id)
);

CREATE TABLE userCollection (
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

CREATE TABLE collectionItem (
    collectionId BIGINT UNSIGNED NOT NULL,
    itemId BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (collectionId, itemId),
    FOREIGN KEY (collectionId)  REFERENCES collection(id),
    FOREIGN KEY (itemId)  REFERENCES item(id)
);

CREATE TABLE fieldType (
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
  FOREIGN KEY (type)  REFERENCES fieldType(id),
  FOREIGN KEY (collectionBaseType)  REFERENCES collectionType(id)
);

CREATE TABLE itemdata (
    itemId BIGINT UNSIGNED NOT NULL,
    fieldId BIGINT UNSIGNED NOT NULL,
    fieldValue VARCHAR(1024),
    PRIMARY KEY (itemId, fieldId, fieldValue),
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





