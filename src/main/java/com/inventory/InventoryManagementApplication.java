/*
 * package com.inventory;
 * 
 * import org.springframework.boot.SpringApplication; import
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration; import
 * org.springframework.boot.autoconfigure.SpringBootApplication; import
 * org.springframework.boot.web.servlet.FilterRegistrationBean; import
 * org.springframework.context.annotation.Bean;
 * 
 * @SpringBootApplication(scanBasePackages = { "com.inventory.controllers",
 * "com.inventory.service", "com.inventory.beans", "com.inventory.factory" })
 * 
 * @EnableAutoConfiguration public class InventoryManagementApplication {
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(InventoryManagementApplication.class, args); }
 * 
 * @Bean public FilterRegistrationBean<RequestFilter> filter() {
 * FilterRegistrationBean<RequestFilter> filterBean = new
 * FilterRegistrationBean<>(); filterBean.setFilter(new RequestFilter());
 * filterBean.addUrlPatterns("/app/*"); return filterBean; } }
 */