/**
 * mysql-rename-routine.sql
 * 重命名存储过程。
 * 适用: MySQL 5.6及以上版本。
 * 建立日期: 2016-01-18
 * 最后编辑: 2016-01-18
 */

DELIMITER ;;
CREATE PROCEDURE rename_routine(IN db VARCHAR(32), IN name VARCHAR(128), IN new_name VARCHAR(128))
BEGIN
    DECLARE origin_mode INT;
    SET origin_mode = @@sql_safe_updates;
    SET @@sql_safe_updates = 0;

    UPDATE IGNORE `mysql`.`proc` 
        SET name = new_name, 
            specific_name = new_name 
        WHERE db = db 
        AND name = name;

    UPDATE IGNORE `mysql`.`procs_priv` 
        SET Routine_name = new_name 
        WHERE Db = db 
        AND Routine_name = name;
    FLUSH PRIVILEGES;

    SET @@sql_safe_updates = origin_mode;
END;;
DELIMITER ;

CALL rename_routine('test', 'add123', 'add');
CALL `add`(1, 2, 3);

