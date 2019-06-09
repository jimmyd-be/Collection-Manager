INSERT INTO cm.collectionType (`id`, `type`, `active`) VALUES
(1, 'Movies', 1),
(2, 'Games', 1);


INSERT INTO cm.fieldType (id, `type`,active) VALUES 
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


INSERT INTO cm.field
(`name`, `type`, `choises`, `required`, `placeHolder`, `label`, `otherOptions`, `collectionBaseType`, `fieldOrder`, `place`, `multiValues`, 
`active`, `labelPosition`, `widget`) VALUES
('title', 1, NULL, 1, 'Title', 'Title', NULL, 1, 0, 'top', 0, 1, 'hidden', 'default'),
('genre', 1, NULL, 0, 'Genre', 'Genre', NULL, 1, 10, 'Main', 1, 1, 'left', 'default'),
('runtime', 12, NULL, 0, '', 'Runtime', NULL, 1, 20, 'Main', 0, 1, 'left', 'default'),
('releaseDate', 4, NULL, 0, 'Release Date', 'Release Date', NULL, 1, 30, 'Main', 0, 1, 'left', 'default'),
('storyline', 3, NULL, 0, 'Storyline', 'Storyline', NULL, 1, 40, 'Main', 0, 1, 'top', 'default'),
('director', 1, NULL, 0, 'Director', 'Director', NULL, 1, 50, 'Main', 1, 1, 'top', 'default'),
('writer', 1, NULL, 0, 'Writer', 'Writer', NULL, 1, 60, 'Main', 1, 1, 'top', 'default'),
('cast', 1, NULL, 0, 'Cast', 'Cast', NULL, 1, 60, 'Main', 1, 1, 'top', 'default'),
('cover', 10, NULL, 0, 'Cover', 'Cover', NULL, 1, 70, 'left', 1, 0, 'hidden', 'image'),
('rating', 9, NULL, 0, 'Rating', 'Rating', NULL, 1, 80, 'left', 1, 0, 'hidden', 'rate');
