package com.anasoft.os.daofusion.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.example.dao.CountryDao;
import com.anasoft.os.daofusion.test.example.dao.CustomerDao;
import com.anasoft.os.daofusion.test.example.dao.OrderDao;
import com.anasoft.os.daofusion.test.example.dao.OrderItemDao;
import com.anasoft.os.daofusion.test.example.dao.PaymentTypeDao;
import com.anasoft.os.daofusion.test.example.dao.StockItemCategoryDao;
import com.anasoft.os.daofusion.test.example.dao.StockItemDao;
import com.anasoft.os.daofusion.test.example.embed.Address;
import com.anasoft.os.daofusion.test.example.entity.Customer;
import com.anasoft.os.daofusion.test.example.entity.Order;
import com.anasoft.os.daofusion.test.example.entity.OrderItem;
import com.anasoft.os.daofusion.test.example.entity.StockItem;
import com.anasoft.os.daofusion.test.example.enums.Country;
import com.anasoft.os.daofusion.test.example.enums.StockItemCategory;

/**
 * Base class for core integration tests aimed
 * at standard persistent entity DAO implementations.
 * 
 * <p>
 * 
 * Core integration tests operate on the sample
 * <em>eShop</em> domain model defined within the
 * <tt>example</tt> subpackage.
 * 
 * <p>
 * 
 * Note that all configuration steps described
 * within the {@link BaseHibernateIntegrationTest}
 * must be followed by specific core integration
 * test subclasses.
 * 
 * @see BaseHibernateIntegrationTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { BaseHibernateCoreIntegrationTest.CONTEXT_LOCATION })
public abstract class BaseHibernateCoreIntegrationTest extends BaseHibernateIntegrationTest {

    protected static final String CONTEXT_LOCATION = "classpath:testContext-hibernate-coreIntegration.xml";
    
    @Autowired
    protected CountryDao countryDao;
    
    @Autowired
    protected CustomerDao customerDao;
    
    @Autowired
    protected OrderDao orderDao;
    
    @Autowired
    protected OrderItemDao orderItemDao;
    
    @Autowired
    protected PaymentTypeDao paymentTypeDao;
    
    @Autowired
    protected StockItemCategoryDao stockItemCategoryDao;
    
    @Autowired
    protected StockItemDao stockItemDao;
    
    protected static final String COUNTRY_SLOVAKIA = "Slovakia";
    protected static final String COUNTRY_JAPAN = "Japan";
    
    protected static final String STOCK_ITEM_CATEGORY_FOOD = "Food";
    protected static final String STOCK_ITEM_CATEGORY_FOOD_DESC = "Food stuff";
    protected static final String STOCK_ITEM_CATEGORY_COMPUTERS = "Computers";
    protected static final String STOCK_ITEM_CATEGORY_COMPUTERS_DESC = "IT stuff";
    
    protected static final int TOTAL_COUNTRY_COUNT = 2;
    protected static final int TOTAL_STOCK_ITEM_CATEGORY_COUNT = 2;
    protected static final int TOTAL_CUSTOMER_COUNT = 2;
    protected static final int TOTAL_ORDER_COUNT = TOTAL_CUSTOMER_COUNT;
    protected static final int ORDER_PER_CUSTOMER_COUNT = 1;
    protected static final int ORDER_ITEM_PER_ORDER_COUNT = 4;
    protected static final int TOTAL_ORDER_ITEM_COUNT = TOTAL_ORDER_COUNT * ORDER_ITEM_PER_ORDER_COUNT;
    
    protected Long customerOneId;
    protected Long customerTwoId;
    
    protected String customerOneEmail;
    protected Address orderOneShippingAddress;
    
    protected static final String CUSTOMER_ONE_EMAIL_PREFIX = "awesome-rambo";
    protected static final String CUSTOMER_TWO_EMAIL_PREFIX = "chucky";
    protected static final String CUSTOMER_EMAIL_SUFFIX = "-provider.net";
    protected static final String CUSTOMER_ONE_FIRST_NAME = "Johnny";
    protected static final String CUSTOMER_TWO_FIRST_NAME = "Chuck";
    protected static final String CUSTOMER_TWO_LAST_NAME_PREFIX = "Mc";
    
    /**
     * Set up the test data shared by all test cases.
     */
    @Before
    public void setupTestData() {
        createEnumerationTestData();
        createEntityTestData();
    }
    
    /**
     * Create test data regarding persistent enumerations.
     */
    private void createEnumerationTestData() {
        Country country = new Country();
        country.setName(COUNTRY_SLOVAKIA);
        countryDao.saveOrUpdate(country);
        
        country = new Country();
        country.setName(COUNTRY_JAPAN);
        countryDao.saveOrUpdate(country);
        
        StockItemCategory stockItemCategory = new StockItemCategory();
        stockItemCategory.setName(STOCK_ITEM_CATEGORY_FOOD);
        stockItemCategory.setDescription(STOCK_ITEM_CATEGORY_FOOD_DESC);
        stockItemCategoryDao.saveOrUpdate(stockItemCategory);
        
        stockItemCategory = new StockItemCategory();
        stockItemCategory.setName(STOCK_ITEM_CATEGORY_COMPUTERS);
        stockItemCategory.setDescription(STOCK_ITEM_CATEGORY_COMPUTERS_DESC);
        stockItemCategoryDao.saveOrUpdate(stockItemCategory);
        
        assertThat(countryDao.countAll(Country.class), equalTo(TOTAL_COUNTRY_COUNT));
        assertThat(stockItemCategoryDao.countAll(StockItemCategory.class), equalTo(TOTAL_STOCK_ITEM_CATEGORY_COUNT));
    }
    
    /**
     * Create test data regarding persistent entities.
     * 
     * <p>
     * 
     * Each {@link Customer} instance is associated to a single {@link Order}
     * instance which contains multiple {@link OrderItem} instances. Each
     * {@link OrderItem} instance is associated to a unique {@link StockItem}
     * instance.
     */
    private void createEntityTestData() {
        Country countrySlovakia = countryDao.getByName(COUNTRY_SLOVAKIA, Country.class);
        Country countryJapan = countryDao.getByName(COUNTRY_JAPAN, Country.class);
        
        StockItemCategory categoryFood = stockItemCategoryDao.getByName(STOCK_ITEM_CATEGORY_FOOD, StockItemCategory.class);
        StockItemCategory categoryComputers = stockItemCategoryDao.getByName(STOCK_ITEM_CATEGORY_COMPUTERS, StockItemCategory.class);
        
        // Customer #1
        Customer customerOne = new Customer();
        customerOne.setFirstName(CUSTOMER_ONE_FIRST_NAME);
        customerOne.setLastName("Rambo");
        customerOne.setEmail(CUSTOMER_ONE_EMAIL_PREFIX + "@some" + CUSTOMER_EMAIL_SUFFIX);
        
        Calendar calendarOne = Calendar.getInstance();
        
        Order orderOne = new Order();
        orderOne.setCreationDate(calendarOne.getTime());
        orderOne.setComplete(false);
        orderOne.setDescription("one");
        customerOne.addOrder(orderOne);
        
        Address shippingAddressOne = new Address();
        shippingAddressOne.setStreet("Blossom street 5");
        shippingAddressOne.setCity("Bratislava");
        shippingAddressOne.setZip("81234");
        shippingAddressOne.setCountry(countrySlovakia);
        
        Address billingAddressOne = new Address();
        shippingAddressOne.setStreet("Tokio Hotel");
        shippingAddressOne.setCity("Tokio");
        shippingAddressOne.setZip("98765");
        shippingAddressOne.setCountry(countryJapan);
        
        orderOne.setShippingAddress(shippingAddressOne);
        orderOne.setBillingAddress(billingAddressOne);
        
        for (int i = 0; i < ORDER_ITEM_PER_ORDER_COUNT; i++) {
            StockItem stockItem = new StockItem();
            stockItem.setName("Stock item " + stockItem.getOid());
            stockItem.setPrice(i);
            stockItem.setCategory(i % 2 == 0 ? categoryFood : categoryComputers);
            stockItem.setDescription(null);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setStockItem(stockItem);
            orderItem.setQuantity(i + 1);
            
            orderOne.addOrderItem(orderItem);
        }
        
        customerOneId = customerDao.saveOrUpdate(customerOne).getId();
        customerOneEmail = customerOne.getEmail();
        orderOneShippingAddress = shippingAddressOne;
        
        // Customer #2
        Customer customerTwo = new Customer();
        customerTwo.setFirstName(CUSTOMER_TWO_FIRST_NAME);
        customerTwo.setLastName(CUSTOMER_TWO_LAST_NAME_PREFIX + "Norris");
        customerTwo.setEmail(CUSTOMER_TWO_EMAIL_PREFIX + "@other" + CUSTOMER_EMAIL_SUFFIX);
        
        Calendar calendarTwo = (Calendar) calendarOne.clone();
        calendarTwo.add(Calendar.DAY_OF_YEAR, -1);
        
        Order orderTwo = new Order();
        orderTwo.setCreationDate(calendarTwo.getTime());
        orderTwo.setComplete(true);
        orderTwo.setDescription("two");
        customerTwo.addOrder(orderTwo);
        
        Address shippingAddressTwo = new Address();
        shippingAddressTwo.setStreet("Apple street 10");
        shippingAddressTwo.setCity("Kosice");
        shippingAddressTwo.setZip("01234");
        shippingAddressTwo.setCountry(countrySlovakia);
        
        orderTwo.setShippingAddress(shippingAddressTwo);
        orderTwo.setBillingAddress(billingAddressOne);
        
        for (int i = 0; i < ORDER_ITEM_PER_ORDER_COUNT; i++) {
            StockItem stockItem = new StockItem();
            stockItem.setName("Stock item " + stockItem.getOid());
            stockItem.setPrice(i);
            stockItem.setCategory(i % 2 == 0 ? categoryFood : categoryComputers);
            stockItem.setDescription(null);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setStockItem(stockItem);
            orderItem.setQuantity(i + 1);
            
            orderTwo.addOrderItem(orderItem);
        }
        
        customerTwoId = customerDao.saveOrUpdate(customerTwo).getId();
        
        assertThat(customerDao.countAll(Customer.class), equalTo(TOTAL_CUSTOMER_COUNT));
        assertThat(orderDao.countAll(Order.class), equalTo(TOTAL_ORDER_COUNT));
        assertThat(orderItemDao.countAll(OrderItem.class), equalTo(TOTAL_ORDER_ITEM_COUNT));
        assertThat(stockItemDao.countAll(StockItem.class), equalTo(TOTAL_ORDER_ITEM_COUNT));
    }
    
}
