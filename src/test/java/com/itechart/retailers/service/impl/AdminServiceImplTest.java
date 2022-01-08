package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.exception.UndefinedLocationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
    @InjectMocks private AdminServiceImpl underTest;

    private Address address = Address.builder()
            .city("New York")
            .firstLine("House no.45 second floor, 5th cross.")
            .stateCode("NY")
            .build();

    @Test
    void shouldSaveLocationAndAddress() {
        //given
        Location location = Location.builder()
                .address(new Address(2L))
                .build();
        //when
        underTest.createLocation(location);
        //then
        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepo).save(addressArgumentCaptor.capture());
        assertThat(addressArgumentCaptor.getValue()).isEqualTo(location.getAddress());

        verify(securityService).getCurrentCustomerId();
        ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepo).save(locationArgumentCaptor.capture());
        assertThat(locationArgumentCaptor.getValue()).isEqualTo(location);
        verifyNoMoreInteractions(addressRepo, securityService, locationRepo);
    }

    @Test
    void shouldCreateUserWithoutLocation() throws UndefinedLocationException {
        //given
        String roleStr = "DIRECTOR";
        Role role = new Role(roleStr);
        User user = User.builder()
                .address(address)
                .name("User")
                .email("employee@work.com")
                .role(role)
                .birthday(LocalDate.now())
                .build();
        given(addressRepo.save(address)).willReturn(address);
        long customerId = 1L;
        given(securityService.getCurrentCustomerId()).willReturn(customerId);
        //when
        underTest.createUser(user);
        //then
        verify(addressRepo).save(address);
        assertThat(user.isActive()).isTrue();
        assertThat(user.getCustomer().getId()).isEqualTo(customerId);
        verify(roleService).save(roleStr);
        assertThat(user.getLocation() == null).isTrue();
        verify(userRepo).save(user);
        verifyNoMoreInteractions(addressRepo, securityService, roleService, userRepo);
    }

    @Test
    void shouldCreateUserWithLocation() throws UndefinedLocationException {
        //given
        String roleStr = "SHOP_MANAGER";
        Role role = new Role(roleStr);
        String locIdentifier = "Location1";
        Location location = Location.builder()
                .identifier(locIdentifier)
                .build();
        User user = User.builder()
                .address(address)
                .role(role)
                .location(location)
                .build();
        given(addressRepo.save(address)).willReturn(address);
        given(locationRepo.findLocationByIdentifier(locIdentifier)).willReturn(Optional.of(location));
        //when
        underTest.createUser(user);
        //then
        verify(locationRepo).findLocationByIdentifier(locIdentifier);
        verifyNoMoreInteractions(addressRepo, locationRepo);
    }

    @Test
    void createSupplier() {
        //given
        Set<Warehouse> warehouses = new HashSet<>();
        warehouses.add(Warehouse.builder()
                .address(address)
                .name("wh1")
                .build());
        Supplier supplier = Supplier.builder()
                .warehouses(warehouses)
                .identifier("sup")
                .name("supplier")
                .build();
        Set<Supplier> suppliers = new HashSet<>(Arrays.asList(supplier));
        Customer customer = Customer.builder()
                .suppliers(suppliers)
                .name("Customer")
                .build();
        given(supplierRepo.save(supplier)).willReturn(supplier);
        long customerId = 1L;
        given(securityService.getCurrentCustomerId()).willReturn(customerId);
        given(customerRepo.findById(customerId)).willReturn(Optional.of(customer));
        //when
        underTest.createSupplier(supplier);
        //then
        for (Warehouse wh: supplier.getWarehouses()) {
            assertThat(wh.getSupplier()).isEqualTo(supplier);
            verify(addressRepo).save(wh.getAddress());
        }
        verify(supplierRepo).save(supplier);
        verify(warehouseRepo).saveAll(warehouses);
        verify(customerRepo).findById(customerId);
        verifyNoMoreInteractions(supplierRepo, warehouseRepo, customerRepo, addressRepo, securityService);
    }
}