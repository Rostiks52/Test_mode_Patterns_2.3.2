package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

import static io.restassured.RestAssured.given;

import java.util.Locale;

@Data
@AllArgsConstructor

public class DataGenerator {
    public static class Authorization {
        private Authorization() {
        }

        public static CustomerData generateAuthorizationActiveUsers() {
            Faker faker = new Faker(new Locale("en"));
            return new CustomerData(
                    faker.name().username(),
                    faker.internet().password(), "active");
        }

        public static CustomerData ganerateAuthorizationBlockedUsers() {
            Faker faker = new Faker(new Locale("en"));
            return new CustomerData(
                    faker.name().username(),
                    faker.internet().password(), "blocked");
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static CustomerData registrationActiveUsers() {
            CustomerData customerData = generateAuthorizationActiveUsers();
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonCustomerData = gsonBuilder.toJson(customerData);
            given()
                    .spec(requestSpec)
                    .body(jsonCustomerData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return customerData;
        }

        public static CustomerData registrationBlockedUsers() {
            CustomerData customerData = ganerateAuthorizationBlockedUsers();
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonCustomerData = gsonBuilder.toJson(customerData);
            given()
                    .spec(requestSpec)
                    .body(jsonCustomerData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return customerData;
        }

        public static String wrongLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }

        public static String wrongPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }
    }



}
