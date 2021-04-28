package ru.inovus.numbergenerator.service.util;

import ru.inovus.numbergenerator.dto.RegistrationNumberDto;

public interface IncrementAble<T> {
    T increment(RegistrationNumberDto registrationNumberDto);
}
