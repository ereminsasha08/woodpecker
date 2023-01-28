package com.woodpecker.woodpecker;

import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class WoodpeckerApplication implements ApplicationRunner {

    private final GeographyMapRepository mapRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(WoodpeckerApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args){
//        List<GeographyMap> all = mapRepository.findAll();
//        all.stream().peek((map) -> orderRepository.save(new OrderMap(map.getDateTime().plusWeeks(2).plusDays(4), map))).toList();
    }
}
