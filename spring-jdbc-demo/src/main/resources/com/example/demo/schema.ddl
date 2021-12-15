DECLARE
   table_does_not_exist   EXCEPTION;
   PRAGMA EXCEPTION_INIT (table_does_not_exist, -942); -- ORA-00942: table or view does not exist

   -- Constant variable for table name
   v_table_name     CONSTANT VARCHAR2(5) := 'TODOS';

   -- Constant variable for DDL
   v_ddl            CONSTANT VARCHAR2(4000) :=
'CREATE TABLE ' || v_table_name ||'
(
    id   NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    data VARCHAR2 (32767)
    CONSTRAINT ensure_json CHECK (data IS JSON)
)
';

BEGIN
    BEGIN
        -- Drop table beforehand
        EXECUTE IMMEDIATE 'DROP TABLE ' || v_table_name;
    EXCEPTION WHEN table_does_not_exist THEN
        -- Ignore ORA-00942: table of view does not exist error
        NULL;
    END;
    EXECUTE IMMEDIATE v_ddl;
END;
