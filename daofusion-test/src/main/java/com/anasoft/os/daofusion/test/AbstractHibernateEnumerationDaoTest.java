package com.anasoft.os.daofusion.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

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

    protected static final String PAYMENT_TYPE_CREDIT_CARD = "Credit card";
    protected static final String PAYMENT_TYPE_CHECK = "Check";
    protected static final String PAYMENT_TYPE_CASH = "Cash";
    
    /**
     * @return Sample {@link PaymentType} transient instance.
     */
    private PaymentType getSamplePaymentTypeTransient(String name) {
        PaymentType paymentTypeTransient = new PaymentType();
        paymentTypeTransient.setName(name);
        
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
     * Test for {@link PersistentEnumerationDao#getByName(String, Class)}:
     * retrieving a persistent enumeration instance.
     */
    @Test
    public void testGetByName_retrievingPersistentEnumerationInstance() {
        PaymentType paymentTypeDetached = paymentTypeDao.saveOrUpdate(getSamplePaymentTypeTransient(PAYMENT_TYPE_CREDIT_CARD));
        
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
     * Test for {@link PersistentEnumerationDao#getByName(String, Class)}:
     * trying to retrieve a non-existing persistent enumeration instance.
     */
    @Test
    public void testGetByName_retrievingNonExistingPersistentEnumerationInstance() {
        PaymentType paymentTypePersistentNonExisting = paymentTypeDao.getByName("Non-existing payment type", PaymentType.class);
        
        assertThat(paymentTypePersistentNonExisting, nullValue());
        assertThat(paymentTypeDao.count(new NestedPropertyCriteria(), PaymentType.class), equalTo(0));
    }
    
    /**
     * Test for {@link PersistentEnumerationDao#getByNames(Class, String...)}:
     * retrieving multiple persistent enumeration instances.
     */
    @Test
    public void testGetByNames_retrievingPersistentEnumerationInstances() {
        PaymentType paymentTypeOne = paymentTypeDao.saveOrUpdate(getSamplePaymentTypeTransient(PAYMENT_TYPE_CREDIT_CARD));
        PaymentType paymentTypeTwo = paymentTypeDao.saveOrUpdate(getSamplePaymentTypeTransient(PAYMENT_TYPE_CHECK));
        PaymentType paymentTypeThree = paymentTypeDao.saveOrUpdate(getSamplePaymentTypeTransient(PAYMENT_TYPE_CASH));
        
        assertThat(paymentTypeDao.count(new NestedPropertyCriteria(), PaymentType.class), equalTo(3));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeOne), equalTo(true));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeTwo), equalTo(true));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeThree), equalTo(true));
        
        paymentTypeDao.getHibernateSession().evict(paymentTypeOne);
        paymentTypeDao.getHibernateSession().evict(paymentTypeTwo);
        paymentTypeDao.getHibernateSession().evict(paymentTypeThree);
        
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeOne), equalTo(false));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeTwo), equalTo(false));
        assertThat(paymentTypeDao.getHibernateSession().contains(paymentTypeThree), equalTo(false));
        
        List<PaymentType> paymentTypeList = paymentTypeDao.getByNames(PaymentType.class, PAYMENT_TYPE_CHECK, PAYMENT_TYPE_CASH);
        
        assertThat(paymentTypeList.size(), equalTo(2));
        
        assertThat(paymentTypeList.contains(paymentTypeTwo), equalTo(true));
        assertThat(paymentTypeList.contains(paymentTypeThree), equalTo(true));
    }
    
}
