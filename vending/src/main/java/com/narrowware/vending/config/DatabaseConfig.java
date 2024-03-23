//package com.narrowware.vending.config;
//
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Configuration
//public class DatabaseConfig {
//
//    @Bean
//    public ApplicationRunner applicationRunner(JdbcTemplate jdbcTemplate) {
//        return args -> {
//            jdbcTemplate.execute("CREATE OR REPLACE FUNCTION check_product_count() " +
//                    "RETURNS TRIGGER AS $$ " +
//                    "BEGIN " +
//                    "IF (SELECT COUNT(*) FROM product) > 10 THEN " +
//                    "RAISE EXCEPTION 'Vending machine is full'; " +
//                    "END IF; " +
//                    "RETURN NEW; " +
//                    "END; " +
//                    "$$ LANGUAGE plpgsql;");
//
//            jdbcTemplate.execute("CREATE TRIGGER product_count_trigger " +
//                    "BEFORE INSERT OR UPDATE OR DELETE ON product " +
//                    "FOR EACH ROW EXECUTE FUNCTION check_product_count();");
//        };
//    }
//}