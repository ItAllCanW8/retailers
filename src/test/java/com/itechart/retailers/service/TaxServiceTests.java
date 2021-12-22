package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.StateCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaxServiceTests {
    @Autowired
    private TaxService taxService;

    @Test
    void loadStateTax() {
        System.out.println(taxService.loadStateTax(StateCode.valueOf("NY")).get());
    }

    @Test
    void loadRentalTax() {
        System.out.println(taxService.loadRentalTax(28L).get());
    }

    @Test
    void loadCategoryTax() {
        System.out.println(taxService.loadItemCategoryTax(1L, 1L).get());
    }

    @Test
    void updateRentalTax() {
        taxService.updateRentalTax(28L, 0.05f);
    }

    @Test
    void updateItemCategoryTax() {
        taxService.updateItemCategoryTax(0.05f, 1L, 1L);
    }
}
