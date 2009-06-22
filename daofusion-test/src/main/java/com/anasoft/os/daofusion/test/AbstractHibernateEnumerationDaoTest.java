package com.anasoft.os.daofusion.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.anasoft.os.daofusion.AbstractHibernateEnumerationDao;
import com.anasoft.os.daofusion.PersistentEnumerationDao;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.test.example.enums.PaymentType;

/**
 * Core integration test for {@link AbstractHibernateEnumerationDao}.
 * 
 * @see BaseHibernateCoreIntegrationTest
 * 
 * @author vojtech.szocs
 */
public abstract class AbstractHibernateEnumerationDaoTest extends BaseHibernateCoreIntegrationTest {

    /**
     * @return Sample {@link PaymentType} transient instance.
     */
    private PaymentType getSamplePaymentTypeTransient() {
        PaymentType paymentTypeTransient = new PaymentType();
        paymentTypeTransient.setName("Credit card");
        
        assertThat(paymentTypeTransient.getId(), nullValue());
        
        return paymentTypeTransient;
    }
    
    /**
     * Verify the test data shared by all test case methods.
     */
    @Before
    public void verifyTestData() {
        assertThat(paymentTypeDao.count(new NestedPropertyCriteria(), PaymentType.class), equalTo(0));
        
        paymentTypeDao.getHibernateSession().clear();
    }
    
    /**
     * Test for {@link PersistentEnumerationDao#get(String, Class)}:
     * retrieving a persistent enumeration instance.
     */
    @Test
    public void testGet_retrievingPersistentEnumerationInstance() {
        PaymentType paymentTypeDetached = paymentTypeDao.saveOrUpdate(getSamplePaymentTypeTransient());
        
        assertThat(paymentTypeDao.count(new NestedPropertyCriteria(), PaymentType.class), equalTo(1));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeDetached), equalTo(true));
        
        paymentTypeDao.getHibernateSession().evict(paymentTypeDetached);
        
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeDetached), equalTo(false));
        
        PaymentType paymentTypePersistent = paymentTypeDao.getByName(paymentTypeDetached.getName(), PaymentType.class);
        
        assertThat(paymentTypePersistent, notNullValue());
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypePersistent), equalTo(true));
        assertThat(paymentTypePersistent.getId(), equalTo(paymentTypeDetached.getId()));
        assertThat(paymentTypePersistent.getName(), equalTo(paymentTypeDetached.getName()));
    }
    
    /**
     * Test for {@link PersistentEnumerationDao#get(String, Class)}:
     * trying to retrieve a non-existing persistent enumeration instance.
     */
    @Test
    public void testGet_retrievingNonExistingPersistentEnumerationInstance() {
        PaymentType paymentTypePersistentNonExisting = paymentTypeDao.getByName("Non-existing payment type", PaymentType.class);
        
        assertThat(paymentTypePersistentNonExisting, nullValue());
        assertThat(paymentTypeDao.count(new NestedPropertyCriteria(), PaymentType.class), equalTo(0));
    }
    
}
