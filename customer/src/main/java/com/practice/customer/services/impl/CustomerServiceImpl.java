package com.practice.customer.services.impl;

import com.github.dozermapper.core.Mapper;
import com.practice.amqp.RabbitMQMessageProducer;
import com.practice.clients.dto.NotificationRequest;
import com.practice.clients.fraud.FraudClient;
import com.practice.clients.dto.FraudCheckResponse;
import com.practice.clients.notification.NotificationClient;
import com.practice.customer.dto.model.CustomerDto;
import com.practice.customer.model.Customer;
import com.practice.customer.repository.CustomerRepository;
import com.practice.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Mapper mapper;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {
        final Customer customer = mapper.map(customerDto, Customer.class);

        Customer customer1 = customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }


             NotificationRequest notificationRequest = new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to razycode...",
                                customer.getFirstName())
                );

            rabbitMQMessageProducer.publish(
                    notificationRequest,
                    "internal.exchange",
                    "internal.notification.routing-key"
            );


        return mapper.map(customer1, CustomerDto.class);
    }
}
