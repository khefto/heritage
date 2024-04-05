package com.hefto.heritage.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PersonLoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(PersonLoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {

        return args -> {
            PersonEntity margrethe = new PersonEntity("Margrethe", null, null);
            log.info("Preloading " + repository.save(margrethe));
            PersonEntity henrik = new PersonEntity("Henrik", null, null);
            log.info("Preloading " + repository.save(henrik));
            PersonEntity frederik = new PersonEntity("Frederik", henrik, margrethe);
            log.info("Preloading " + repository.save(frederik));
            PersonEntity joachim = new PersonEntity("Joachim", henrik, margrethe);
            log.info("Preloading " + repository.save(joachim));
        };
    }
}