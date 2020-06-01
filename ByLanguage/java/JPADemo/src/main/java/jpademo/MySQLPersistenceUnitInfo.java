package jpademo;

import javax.persistence.*;
import javax.persistence.spi.*;
import javax.sql.*;
import java.util.*;
import org.hibernate.jpa.*;
import java.net.*;

// https://palashray.com/jpa-creating-entitymanager-without-persistence-xml/
public class MySQLPersistenceUnitInfo implements PersistenceUnitInfo {

    Properties props = new Properties();

    /**
       DB2	 jdbc:db2://localhost:5000/db_myDB，其中db_myDB为数据库名
       PostgreSQL	jdbc:postgresql://localhost/myDB
       MySQL	jdbc:mysql://localhost:3306/db_myDB，其中db_myDB为数据库名
       Oracle	jdbc:oracle:thin:@localhost:1521:db_myDB，其中db_myDB为数据库的SID
       Sybase	jdbc:sybase:Tds:localhost:5007/db_myDB，其中db_myDB为数据库名
       Microsoft SQL Server	jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=db_myDB，其中db_myDB为数据库名
       Informix	jdbc:informix-sqli://123.45.67.89:1533/db_myDB:INFORMIXSERVER=myserver；user=testuser;password=testpassword，其中db_myDB为数据库名
    */

    public MySQLPersistenceUnitInfo(String host, int port, String user, String password, String database, String timeZone) {
        String url = String.format("jdbc:mysql://%s:%d/%s?serverTimezone=%s", host, port, database, timeZone);
        
		props.put("hibernate.connection.url", url);
		props.put("hibernate.connection.username", user);
        props.put("hibernate.connection.password", password);
        props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    @Override
    public List<String> getManagedClassNames() {
        return Arrays.asList(GuestBook.class.getName());
    }

    @Override
    public String getPersistenceUnitName() {
        return "TestUnit";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return null;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
		
}
