package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.payload.request.SignUpReq;
import com.itechart.retailers.model.payload.response.CustomerPageResp;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;

import java.io.IOException;

/**
 * The interface Customer service.
 */
public interface CustomerService {

	/**
	 * Save customer.
	 *
	 * @param customer the customer
	 * @return the customer
	 */
	Customer save(Customer customer);

	/**
	 * Find by params customer page resp.
	 *
	 * @param active the active
	 * @param page   the page
	 * @return the customer page resp
	 */
	CustomerPageResp findByParams(Boolean active, Integer page);

	/**
	 * Gets by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	Customer getById(Long id);

	/**
	 * Change user status.
	 *
	 * @param customerId the customer id
	 * @param active     the active
	 */
	void changeUserStatus(Long customerId, boolean active);

	/**
	 * Create new Customer with User of this customer with 'ADMIN' role.
	 *
	 * @param name  name of the customer
	 * @param email email of the admin-user
	 * @return registered customer
	 */
	Customer registerCustomer(String name, String email) throws IOException, MailIsAlreadyInUse;

}
