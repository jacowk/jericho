package za.co.jericho.account.service;

import java.util.Date;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import za.co.jericho.account.domain.Account;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-13
 */
public class ManageAccountServiceBeanTest {
    
    public ManageAccountServiceBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

//    /**
//     * Test of getEntityManager method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testGetEntityManager() throws Exception {
//        System.out.println("getEntityManager");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        EntityManager expResult = null;
//        EntityManager result = instance.getEntityManager();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setEntityManager method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testSetEntityManager() throws Exception {
//        System.out.println("setEntityManager");
//        EntityManager entityManager = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        instance.setEntityManager(entityManager);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of addAccount method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testAddAccount() throws Exception {
//        System.out.println("addAccount");
//        Account account = new Account();
//        account.setName("Profit and Loss Account");
//        account.setCreateDate(new Date());
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        Account expResult = null;
//        Account result = instance.addAccount(account);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of updateAccount method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testUpdateAccount() throws Exception {
//        System.out.println("updateAccount");
//        Account account = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        Account expResult = null;
//        Account result = instance.updateAccount(account);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of markAccountDeleted method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testMarkAccountDeleted() throws Exception {
//        System.out.println("markAccountDeleted");
//        Account account = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        Account expResult = null;
//        Account result = instance.markAccountDeleted(account);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of searchAcounts method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testSearchAcounts() throws Exception {
//        System.out.println("searchAcounts");
//        AccountSearchCriteria accountSearchCriteria = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        List<Account> expResult = null;
//        List<Account> result = instance.searchAcounts(accountSearchCriteria);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of calculateProfitOrLoss method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testCalculateProfitOrLoss() throws Exception {
//        System.out.println("calculateProfitOrLoss");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        instance.calculateProfitOrLoss();
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAccount method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testFindAccount() throws Exception {
//        System.out.println("findAccount");
//        Object id = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        Account expResult = null;
//        Account result = instance.findAccount(id);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllAccounts method, of class ManageAccountServiceBean.
//     */
//    @Test
//    public void testFindAllAccounts() throws Exception {
//        System.out.println("findAllAccounts");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        ManageAccountServiceBean instance = (ManageAccountServiceBean)container.getContext().lookup("java:global/classes/ManageAccountServiceBean");
//        List<Account> expResult = null;
//        List<Account> result = instance.findAllAccounts();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
