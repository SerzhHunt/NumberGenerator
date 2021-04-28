package ru.inovus.numbergenerator.unit.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.inovus.numbergenerator.controller.RegistrationNumberController;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;
import ru.inovus.numbergenerator.service.impl.RegistrationNumberServiceImpl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationNumberController.class)
class RegistrationNumberMvcTest {

    private static final String RANDOM_URL = "/number/random";
    private static final String NEXT_URL = "/number/next";
    private static final String REGISTRATION_NUMBER = "A001AA";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistrationNumberServiceImpl service;

    @BeforeEach
    public void setUp() {
        RegistrationNumberDto registrationNumberDto = RegistrationNumberDto.builder()
                .registrationPlate(REGISTRATION_NUMBER)
                .region(Region.TATARSTAN.getRegion())
                .country(Country.RUS)
                .build();

        when(service.getNextRegistrationNumber(Region.TATARSTAN, Country.RUS)).thenReturn(registrationNumberDto);
        when(service.getRandomRegistrationNumber(Region.TATARSTAN, Country.RUS)).thenReturn(registrationNumberDto);
    }

    @Test
    void shouldReturn200CodeAndAuthorWhenSuccessfullyReturnRandomRegNumber() throws Exception {
        this.mvc.perform(get(RANDOM_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(REGISTRATION_NUMBER.concat(" ")
                        .concat(String.valueOf(Region.TATARSTAN.getRegion()))
                        .concat(Country.RUS.name())))
                .andReturn();
    }

    @Test
    void shouldReturn200CodeAndAuthorWhenSuccessfullyReturnNextRegNumber() throws Exception {
        this.mvc.perform(get(NEXT_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(REGISTRATION_NUMBER.concat(" ")
                        .concat(String.valueOf(Region.TATARSTAN.getRegion()))
                        .concat(Country.RUS.name())))
                .andReturn();
    }
}
