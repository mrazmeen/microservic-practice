package com.practice.customer.dto.model;


import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private static final long serialVersionUID = 5524072518226229591L;
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
