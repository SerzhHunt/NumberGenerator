package ru.inovus.numbergenerator.service.util.impl;

import org.springframework.stereotype.Component;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.exception.ValidationRegistrationNumberException;
import ru.inovus.numbergenerator.service.util.IncrementAble;

import java.util.Arrays;
import java.util.List;

@Component
public class SeriesPlateIncrement implements IncrementAble<RegistrationNumberDto> {

    private static final List<String> SERIES = Arrays.asList("А", "В", "С", "Е", "Н", "К", "М", "О", "Р", "Т", "Х", "У");
    private static final int FIRST_LETTER_CAR_SERIES = 0;
    private static final int SECOND_LETTER_CAR_SERIES = 4;
    private static final int THIRD_LETTER_CAR_SERIES = 5;

    @Override
    public RegistrationNumberDto increment(RegistrationNumberDto registrationNumberDto) {
        String firstLetter = getLetterPositionFromRegNumber(registrationNumberDto, FIRST_LETTER_CAR_SERIES);
        String secondLetter = getLetterPositionFromRegNumber(registrationNumberDto, SECOND_LETTER_CAR_SERIES);
        String thirdLetter = getLetterPositionFromRegNumber(registrationNumberDto, THIRD_LETTER_CAR_SERIES);

        int thirdIndex = SERIES.indexOf(thirdLetter);
        if (thirdIndex < SERIES.size()) {
            return changeLetterCarSeries(registrationNumberDto, thirdIndex, THIRD_LETTER_CAR_SERIES);
        } else {
            int firstIndex = SERIES.indexOf(firstLetter);
            if (firstIndex < SERIES.size()) {
                return changeLetterCarSeries(registrationNumberDto, firstIndex, FIRST_LETTER_CAR_SERIES);
            } else {
                int secondIndex = SERIES.indexOf(secondLetter);
                if (secondIndex < SERIES.size()) {
                    return changeLetterCarSeries(registrationNumberDto, secondIndex, SECOND_LETTER_CAR_SERIES);
                }
            }
        }
        throw new ValidationRegistrationNumberException("Error car series validation");
    }

    private RegistrationNumberDto changeLetterCarSeries(RegistrationNumberDto registrationNumber,
                                                        int index, int letterPosition) {
        String letterCarSeries = getLetterPositionFromRegNumber(registrationNumber, letterPosition);

        registrationNumber.setRegistrationPlate(registrationNumber
                .getRegistrationPlate()
                .replace(letterCarSeries, SERIES.get(index + 1)));

        return registrationNumber;
    }

    private String getLetterPositionFromRegNumber(RegistrationNumberDto registrationNumberDto, int position) {
        return String.valueOf(registrationNumberDto.getRegistrationPlate().charAt(FIRST_LETTER_CAR_SERIES));
    }
}
