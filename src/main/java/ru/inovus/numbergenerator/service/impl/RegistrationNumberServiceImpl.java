package ru.inovus.numbergenerator.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.exception.RegistrationNumberNotFoundException;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;
import ru.inovus.numbergenerator.model.RegistrationNumber;
import ru.inovus.numbergenerator.repository.RegistrationNumberRepository;
import ru.inovus.numbergenerator.service.RegistrationNumberService;
import ru.inovus.numbergenerator.service.util.impl.NumberPlateGenerator;
import ru.inovus.numbergenerator.service.util.impl.NumberPlateIncrement;
import ru.inovus.numbergenerator.service.util.impl.SeriesPlateGenerator;
import ru.inovus.numbergenerator.service.util.impl.SeriesPlateIncrement;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationNumberServiceImpl implements RegistrationNumberService {
    private static final int MIN_VALUE_CAR_NUMBER = 1;

    private final MapperFacade mapper;
    private final RegistrationNumberRepository repository;
    private final NumberPlateGenerator plateGenerator;
    private final SeriesPlateGenerator seriesGenerator;
    private final NumberPlateIncrement plateIncrement;
    private final SeriesPlateIncrement seriesIncrement;

    @Override
    @Transactional
    public RegistrationNumberDto getRandomRegistrationNumber(Region region, Country country) {
        String generatedNumber = getRegistrationNumber(plateGenerator.generate(), seriesGenerator.generate());

        return this.saveRegistrationNumber(buildRegistrationNumberDto(generatedNumber, region, country));
    }

    @Override
    @Transactional
    public RegistrationNumberDto getNextRegistrationNumber(Region region, Country country) {
        Optional<RegistrationNumber> regNumber = repository.findFirstByOrderByIdDesc();

        if (regNumber.isEmpty()) {
            throw new RegistrationNumberNotFoundException("Registration numbers not found");
        }

        RegistrationNumberDto nextRegNumberDto = generateNextRegistrationNumber(
                mapper.map(regNumber.get(), RegistrationNumberDto.class));

        return this.saveRegistrationNumber(nextRegNumberDto);
    }

    @Override
    @Transactional
    public RegistrationNumberDto saveRegistrationNumber(RegistrationNumberDto registrationNumberDto) {
        RegistrationNumber savedNumber = repository.save(mapper.map(registrationNumberDto, RegistrationNumber.class));
        return mapper.map(savedNumber, RegistrationNumberDto.class);
    }

    private RegistrationNumberDto generateNextRegistrationNumber(RegistrationNumberDto registrationNumber) {
        RegistrationNumberDto newRegistrationPlate = plateIncrement.increment(registrationNumber);

        if (getOnlyRegNumber(newRegistrationPlate) > MIN_VALUE_CAR_NUMBER) {
            return newRegistrationPlate;
        }
        return seriesIncrement.increment(newRegistrationPlate);
    }

    private Integer getOnlyRegNumber(RegistrationNumberDto registrationNumberDto) {
        return Integer.parseInt(registrationNumberDto.getRegistrationPlate().substring(1, 4));
    }

    private String getRegistrationNumber(String number, String series) {
        return series.charAt(0) + number + series.substring(1);
    }

    private RegistrationNumberDto buildRegistrationNumberDto(String registrationPlate, Region region, Country country) {
        return RegistrationNumberDto.builder()
                .registrationPlate(registrationPlate)
                .region(region.getRegion())
                .country(country)
                .build();
    }
}
