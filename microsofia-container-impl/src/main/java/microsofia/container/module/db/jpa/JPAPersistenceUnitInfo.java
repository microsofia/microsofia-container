package microsofia.container.module.db.jpa;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;

/**
 * Implementation of the PersistentUnitInfo used to create Hibernate EntityManagerFactory.
 * */
class JPAPersistenceUnitInfo implements PersistenceUnitInfo{
	private String persistenceUnitName;
    private PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    private List<String> managedClassNames;
    private Properties properties;
    private DataSource jtaDataSource;
    private DataSource nonJtaDataSource;

    public JPAPersistenceUnitInfo(String persistenceUnitName,List<String> managedClassNames,Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

    /**
     * Returns the persistent-unit name.
     * */
    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    /**
     * Returns the provider class name.
     * */
    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    /**
     * Returns the transaction type. For the moment, no XA implementation!
     * */
    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * It shouldn't be called.
     * */
    @Override
    public DataSource getJtaDataSource() {
        return jtaDataSource;
    }

    /**
     * It shouldn't be called.
     * */
    public JPAPersistenceUnitInfo setJtaDataSource(DataSource jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
        this.nonJtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.JTA;
        return this;
    }

    /**
     * Returns the DataSource to use.
     * */
    @Override
    public DataSource getNonJtaDataSource() {
        return nonJtaDataSource;
    }

    /**
     * Sets the DataSource to use.
     * */
    public JPAPersistenceUnitInfo setNonJtaDataSource(DataSource nonJtaDataSource) {
        this.nonJtaDataSource = nonJtaDataSource;
        this.jtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
        return this;
    }

    /**
     * Not used.
     * */
    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    /**
     * Not used.
     * */
    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }

    /**
     * Not used.
     * */
    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    /**
     * Returns the entities class names.
     * */
    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }

    /**
     * Not used.
     * */
    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    /**
     * Not used.
     * */
    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    /**
     * Not used.
     * */
    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    /**
     * Return the properties to configure the EntityManagerFactory.
     * */
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "2.1";
    }

    /**
     * Returns the current classloader.
     * */
    @Override
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    /**
     * Not used.
     * */
    @Override
    public void addTransformer(ClassTransformer transformer) {
    }

    /**
     * Not used.
     * */
    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
