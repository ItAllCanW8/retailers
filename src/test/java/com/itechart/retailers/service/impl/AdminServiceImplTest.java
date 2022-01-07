package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Address;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock private SecurityContextService securityService;
    @Mock private LocationRepository locationRepo;
    @Mock private UserRepository userRepo;
    @Mock private AddressRepository addressRepo;
    @Mock private RoleService roleService;
    @Mock private WarehouseRepository warehouseRepo;
    @Mock private SupplierRepository supplierRepo;
    @Mock private CustomerRepository customerRepo;
    @Mock private PasswordEncoder passwordEncoder;
    private AdminService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AdminServiceImpl(securityService,locationRepo,userRepo,addressRepo,roleService,warehouseRepo,
                supplierRepo,customerRepo,passwordEncoder);
    }

    @Test
    void createLocation() {
        //given
//        Address address = new Address(2L);
        Location location = Location.builder()
                .address(new Address(2L))
                .build();
        //when
        underTest.createLocation(location);
        //then
        ArgumentCaptor<Address> addressArgumentCaptorArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepo).save(addressArgumentCaptorArgumentCaptor.capture());
        assertThat(addressArgumentCaptorArgumentCaptor.getValue()).isEqualTo(location.getAddress());
    }

    @Test
    void deleteLocation() {
    }

    @Test
    void deleteLocations() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUserStatus() {
    }

    @Test
    void createSupplier() {
    }

    @Test
    void findSuppliers() {
    }

    @Test
    void updateSupplierStatus() {
    }
}