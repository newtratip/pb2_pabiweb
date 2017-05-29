CREATE EXTENSION postgres_fdw;
CREATE SERVER foreign_server
        FOREIGN DATA WRAPPER postgres_fdw
        OPTIONS (host '10.226.202.133', port '5432', dbname 'PABI2_v7');
CREATE USER MAPPING FOR alfresco
        SERVER foreign_server
        OPTIONS (user 'openerp', password 'VZbv29Nl');