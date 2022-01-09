package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.WriteOffActService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.WriteOffActAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itechart.retailers.controller.constant.Message.WRITE_OFF_ACT_CREATED_MSG;
import static com.itechart.retailers.security.constant.Authority.WRITE_OFF_ACT_GET_AUTHORITY;
import static com.itechart.retailers.security.constant.Authority.WRITE_OFF_ACT_POST_AUTHORITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WriteOffActController {
	public static final String POST_WRITE_OFF_ACTS_MAPPING = "/write-off-acts";
	public static final String GET_WRITE_OFF_ACTS_MAPPING = "/write-off-acts";
	public static final String GET_LOCAL_WRITE_OFF_ACTS_MAPPING = "/local-write-off-acts";
	public static final String POST_AUTHORITY = "hasAuthority('" + WRITE_OFF_ACT_POST_AUTHORITY + "')";
	public static final String GET_AUTHORITY = "hasAuthority('" + WRITE_OFF_ACT_GET_AUTHORITY + "')";

	private final WriteOffActService writeOffActService;

	@PostMapping(POST_WRITE_OFF_ACTS_MAPPING)
	@PreAuthorize(POST_AUTHORITY)
	public ResponseEntity<?> createWriteOffAct(@RequestBody WriteOffAct writeOffAct) throws ItemAmountException, WriteOffActAlreadyExistsException {
		writeOffActService.save(writeOffAct);
		return ResponseEntity.ok(new MessageResp(WRITE_OFF_ACT_CREATED_MSG));
	}

	@GetMapping(GET_LOCAL_WRITE_OFF_ACTS_MAPPING)
	@PreAuthorize(GET_AUTHORITY)
	public List<WriteOffActDto> loadLocationWriteOffActs() {
		return writeOffActService.loadLocalWriteOffActs();
	}

	@GetMapping(GET_WRITE_OFF_ACTS_MAPPING)
	@PreAuthorize(GET_AUTHORITY)
	public List<WriteOffActDto> loadAllWriteOffActs() {
		return writeOffActService.loadCustomerWriteOffActs();
	}
}
