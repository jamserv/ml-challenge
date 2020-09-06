DROP TABLE IF EXISTS ml.childen;
    
DROP TABLE IF EXISTS ml.item;
    
CREATE TABLE ml.childen (
	item_id VARCHAR(255) NOT NULL,
	stop_time DATETIME,
	item VARCHAR(255),
	primary key (item_id)
) engine=InnoDB;
    
CREATE TABLE ml.item (
	item_id VARCHAR(255) NOT NULL,
	title VARCHAR(255),
	category_id VARCHAR(255),
	price DECIMAL(19,2),
	start_time DATETIME,
	stop_time DATETIME,
	primary key (item_id)
) engine=InnoDB;
    
ALTER TABLE ml.childen 
	ADD constraint 
	foreign key (item) 
	references item (item_id);

