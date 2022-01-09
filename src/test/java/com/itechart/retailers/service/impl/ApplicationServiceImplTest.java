package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationItemReq;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.exception.ApplicationAlreadyExists;
import com.itechart.retailers.service.exception.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.itechart.retailers.controller.constant.Message.ITEM_NOT_FOUND_MSG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @Mock private ApplicationRepository applicationRepository;
    @Mock private LocationRepository locationRepository;
    @Mock private SecurityContextService securityService;
    @Mock private ItemRepository itemRepository;
    @Mock private ApplicationItemRepository applicationItemRepository;
    @Mock private LocationItemRepository locationItemRepository;
    @Captor private ArgumentCaptor<Set<ApplicationItem>> itemAssocArgCaptor;
    @InjectMocks private ApplicationServiceImpl underTest;

    @Test
    void getCurrentApplications() {
    }

    @Test
    void shouldSaveApplication() throws ItemNotFoundException, ApplicationAlreadyExists {
        //given
        ApplicationReq applicationReq = new ApplicationReq();
        applicationReq.setApplicationNumber("app");
        Set<ApplicationItemReq> appItemReqs = new HashSet<>();
        ApplicationItemReq appItem1 = new ApplicationItemReq();
        appItem1.setUpc("item1");
        appItem1.setAmount(3);
        appItem1.setCost(2.2f);
        appItemReqs.add(appItem1);
        applicationReq.setItems(appItemReqs);
        Item item1 = Item.builder()
                .upc(appItem1.getUpc())
                .build();
        Location location = Location.builder()
                .identifier("location")
                .build();
        User user = User.builder()
                .email("email@email.com")
                .build();
        Long customerId = 1L;
        Customer customer = new Customer(customerId);
        Application application = Application.builder()
                .applicationNumber(applicationReq.getApplicationNumber())
                .srcLocation(location)
                .destLocation(location)
                .status("STARTED_PROCESSING")
                .createdBy(user)
                .lastUpdBy(user)
                .regDateTime(LocalDateTime.now())
                .lastUpdDateTime(LocalDateTime.now())
                .customer(customer)
                .build();
        ApplicationItem applicationItem = ApplicationItem.builder()
                .item(item1)
                .amount(appItem1.getAmount())
                .cost(appItem1.getCost())
                .application(application)
                .build();

        Set<ApplicationItem> appItems = new HashSet<>();
        appItems.add(applicationItem);

        given(itemRepository.findItemByUpc(appItem1.getUpc())).willReturn(Optional.of(item1));
        given(securityService.getCurrentUser()).willReturn(user);
        given(securityService.getCurrentLocation()).willReturn(location);
        given(securityService.getCurrentCustomerId()).willReturn(customerId);
        //when
        underTest.save(applicationReq);
        //then
        for (ApplicationItemReq applicationItemReq : applicationReq.getItems()) {
            verify(itemRepository, times(3)).findItemByUpc(applicationItemReq.getUpc());
        }
        verify(securityService, times(2)).getCurrentLocation();
        verify(securityService).getCurrentCustomerId();
        verify(applicationItemRepository).saveAll(itemAssocArgCaptor.capture());
        Set<ApplicationItem> appItemsCaptured = itemAssocArgCaptor.getValue();

        List<ApplicationItem> appItemsCapturedList = appItemsCaptured.stream().toList();
        List<ApplicationItem> appItemList = appItems.stream().toList();

        assertThat(appItemsCapturedList.equals(appItemList)).isTrue();
        ArgumentCaptor<Application> appArgCaptor = ArgumentCaptor.forClass(Application.class);
        verify(applicationRepository).save(appArgCaptor.capture());
        assertThat(appArgCaptor.getValue().equals(application)).isTrue();
        verifyNoMoreInteractions(applicationRepository,applicationItemRepository,securityService,itemRepository);
    }

    @Test
    void shouldItemNotFoundException(){
        //given
        ApplicationReq applicationReq = new ApplicationReq();
        applicationReq.setApplicationNumber("app");
        Set<ApplicationItemReq> appItemReqs = new HashSet<>();
        ApplicationItemReq appItem1 = new ApplicationItemReq();
        appItem1.setUpc("item1");
        appItem1.setAmount(3);
        appItem1.setCost(2.2f);
        appItemReqs.add(appItem1);
        applicationReq.setItems(appItemReqs);
        given(itemRepository.findItemByUpc(appItem1.getUpc())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.save(applicationReq))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining(ITEM_NOT_FOUND_MSG);
    }

    @Test
    void getById() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findApplicationsByDestLocation() {
    }

    @Test
    void getOccupiedCapacity() {
    }

    @Test
    void forwardApplication() {
    }

    @Test
    void dispatchItems() {
    }
}