package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.WriteOffActService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WriteOffActController {

    private final String postAuthorities = "hasAuthority('DISPATCHER') or hasAuthority('SHOP MANAGER')";
    private Long locationId;
    private final UserService userService;
    private final WriteOffActService writeOffActService;

    @PostMapping("/write-off-acts")
    @PreAuthorize(postAuthorities)
    public ResponseEntity<?> createWriteOffAct(@RequestBody WriteOffAct writeOffAct){
        setLocationIdIfNotSet();

        writeOffActService.save(writeOffAct, locationId);

        return ResponseEntity.ok(new MessageResponse("Write-off act created."));
    }

    private void setLocationIdIfNotSet(){
        if(locationId == null){
            String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            locationId = userService.getByEmail(currentEmail).get().getLocation().getId();
        }
    }
}
