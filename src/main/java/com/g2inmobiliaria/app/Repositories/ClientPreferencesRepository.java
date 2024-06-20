package com.g2inmobiliaria.app.Repositories;



import com.g2inmobiliaria.app.Entities.ClientPreference;

import java.math.BigDecimal;
import java.util.Optional;

public interface ClientPreferencesRepository {

    Optional<ClientPreference> findByMinimumPrice(BigDecimal MinimumPrice);
}
