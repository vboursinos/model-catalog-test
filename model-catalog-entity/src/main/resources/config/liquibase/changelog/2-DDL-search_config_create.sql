CREATE TABLE SEARCH_CONFIG  (
	ID  			integer GENERATED ALWAYS AS IDENTITY,
	PACKAGE_HASH  		varchar(300) NOT NULL,
	CLASS_NAME  		varchar(300) NOT NULL,
	FILTER_NAME 		varchar(50) NOT NULL,
	TARGET_NAME 		varchar(50),
	ACTIVE 			integer default 1,

	PRIMARY KEY ( ID )
);

COMMENT ON TABLE SEARCH_CONFIG IS 'Configuration table for the component jpa-search';
COMMENT ON COLUMN SEARCH_CONFIG.ID IS 'Autoincremental identifier';
COMMENT ON COLUMN SEARCH_CONFIG.PACKAGE_HASH IS 'Package hash name where the entity class is contained (mandatory)';
COMMENT ON COLUMN SEARCH_CONFIG.CLASS_NAME IS 'Entity class name target of the search (mandatory)';
COMMENT ON COLUMN SEARCH_CONFIG.FILTER_NAME IS 'Name of the filter name (mandatory)';
COMMENT ON COLUMN SEARCH_CONFIG.TARGET_NAME IS 'Name of the search filter (optional)';
COMMENT ON COLUMN SEARCH_CONFIG.ACTIVE IS 'Boolean value that indicates the row being active.';
