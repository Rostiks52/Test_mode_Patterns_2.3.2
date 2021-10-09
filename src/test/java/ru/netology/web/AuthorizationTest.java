package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReturnHappyPath() {
        CustomerData customerData = DataGenerator.Authorization.registrationActiveUsers();
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(customerData.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible);

    }

    @Test
    void shouldVerifyWrongLoginOfActiveUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationActiveUsers();
        $("[data-test-id='login'] input").setValue(DataGenerator.Authorization.wrongLogin());
        $("[data-test-id='password'] input").setValue(customerData.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldVerifyWrongPasswordOfActiveUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationActiveUsers();
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.Authorization.wrongPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldVerifyBlockedUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationBlockedUsers();
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(customerData.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

}
