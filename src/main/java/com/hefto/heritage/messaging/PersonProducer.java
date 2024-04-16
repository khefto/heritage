package com.hefto.heritage.messaging;

import com.hefto.heritage.dto.Person;

public interface PersonProducer {
    void sendMessage(Long key, Person value);
}
