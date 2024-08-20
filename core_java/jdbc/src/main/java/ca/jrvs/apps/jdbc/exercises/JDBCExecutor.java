package ca.jrvs.apps.jdbc.exercises;

import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.LoggerUtil;

import java.sql.Connection;

public class JDBCExecutor {

    //note: testCustomer id: 10,000
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(
                "localhost", "hplussport", "postgres", "password");
        Connection connection = dcm.getConnection();
        CustomerDAO customerDAO = new CustomerDAO(connection);
        Customer testCustomer = testCustomer();
        testCustomer.setId(10000);
        testCustomer.setFirstName("newName");
        testCustomer = customerDAO.update(testCustomer);
        LoggerUtil.getLogger().debug(testCustomer.getId() + " " + testCustomer.getFirstName());
    }

    private static Customer testCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("testFirstName");
        customer.setLastName("testLastName");
        customer.setEmail("test@email.com");
        customer.setPhone("(111) 222-3333");
        customer.setAddress("Test Street");
        customer.setCity("Test Town");
        customer.setState("TEST");
        customer.setZipCode("12345");
        return customer;
    }
}
