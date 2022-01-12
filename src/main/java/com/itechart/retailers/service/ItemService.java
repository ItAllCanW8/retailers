package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.ItemPageResp;
import com.itechart.retailers.service.exception.ItemAlreadyExistsException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Item service.
 */
public interface ItemService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Item> findAll();

    /**
     * Save item.
     *
     * @param item the item
     * @return the item
     * @throws ItemAlreadyExistsException the item already exists exception
     */
    Item save(Item item) throws ItemAlreadyExistsException;

    /**
     * Gets by id.
     *
     * @param itemId the item id
     * @return the by id
     */
    Item getById(Long itemId);

    /**
     * Delete.
     *
     * @param item the item
     */
    void delete(Item item);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find items by customer id item page resp.
     *
     * @param page the page
     * @return the item page resp
     */
    ItemPageResp findItemsByCustomerId(Integer page);

    /**
     * Find item by upc optional.
     *
     * @param upc the upc
     * @return the optional
     */
    Optional<Item> findItemByUpc(String upc);

    /**
     * Create item.
     *
     * @param item the item
     * @return the item
     * @throws ItemAlreadyExistsException the item already exists exception
     */
    Item create(Item item) throws ItemAlreadyExistsException;

}
