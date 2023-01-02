package com.psp.test.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_detail")
@NoArgsConstructor
@Data
public class UserDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
            
    @NotNull
    @Column(name="company_name")
    private String companyName;
    
    @NotNull
    private int nip;
    
    @NotNull
    private int regon;
    
    @NotNull
    private boolean confirmed;
    
}
