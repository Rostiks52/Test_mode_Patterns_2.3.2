package ru.netology.web;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class CustomerData {
    private String login;
    private String password;
    private String status;
}
