package com.practice.customer.Controller;

import com.practice.customer.dto.model.CustomerDto;
import com.practice.customer.dto.transfer.ResponseDto;
import com.practice.customer.services.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> registerCustomer(@RequestBody CustomerDto customerDto){
        log.info("new customer registration {}",customerDto);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(now())
                        .data(of("customer", customerService.registerCustomer(customerDto)))
                        .message("customer created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()

        );
    }
}
