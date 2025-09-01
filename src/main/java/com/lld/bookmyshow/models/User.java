package com.lld.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class User extends BaseModel {
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    @OneToMany
    private List<Booking> bookings;
}
