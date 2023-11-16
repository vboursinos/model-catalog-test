CREATE TABLE SEARCH_CONFIG  (
	ID  				integer GENERATED ALWAYS AS IDENTITY,
	HASH_PAQUETE  		varchar(300) NOT NULL,
	NOMBRE_CLASE  		varchar(300) NOT NULL,
	NOMBRE_FILTRO 		varchar(50) NOT NULL,
	NOMBRE_DESTINO 		varchar(50),
	ACTIVO  			integer default 1,
	
	PRIMARY KEY ( ID )
);

COMMENT ON TABLE SEARCH_CONFIG IS 'Tabla de configuracion del componente igv-search';
COMMENT ON COLUMN SEARCH_CONFIG.ID IS 'Identificador unico autoincremental';
COMMENT ON COLUMN SEARCH_CONFIG.HASH_PAQUETE IS 'Hash con el nombre del paquete donde está contenida la clase entidad afectada por la validación (obligatorio)';
COMMENT ON COLUMN SEARCH_CONFIG.NOMBRE_CLASE IS 'Nombre de la clase entidad afectada por la validación (obligatorio)';
COMMENT ON COLUMN SEARCH_CONFIG.NOMBRE_FILTRO IS 'Nombre del filtro de entrada (obligatorio)';
COMMENT ON COLUMN SEARCH_CONFIG.NOMBRE_DESTINO IS 'Nombre con el que se quedará el filtro final de la validación (opcional)';
COMMENT ON COLUMN SEARCH_CONFIG.ACTIVO IS 'Boolean que indica si el registro está activo';