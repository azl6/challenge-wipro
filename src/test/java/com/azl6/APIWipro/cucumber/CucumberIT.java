package com.azl6.APIWipro.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.azl6.APIWipro.ApiWiproApplication;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {ApiWiproApplication.class, CucumberIT.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin = {"pretty"}, tags = "", features = "src/test/resources/features")
public class CucumberIT {
    
}
