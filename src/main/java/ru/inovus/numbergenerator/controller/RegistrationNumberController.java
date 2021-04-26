package ru.inovus.numbergenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;
import ru.inovus.numbergenerator.service.impl.RegistrationNumberServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "number")
public class RegistrationNumberController {
    private final RegistrationNumberServiceImpl service;

    @GetMapping("next")
    public String getNext() {
        return service.getNextRegistrationNumber(Region.TATARSTAN, Country.RUS).toString();
    }

    @GetMapping("random")
    public String getRandom() {
        return service.getRandomRegistrationNumber(Region.TATARSTAN, Country.RUS).toString();
    }
}
