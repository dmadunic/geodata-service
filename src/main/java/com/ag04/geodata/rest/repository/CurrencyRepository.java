package com.ag04.geodata.rest.repository;

import com.ag04.geodata.rest.domain.Currency;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Currency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>, JpaSpecificationExecutor<Currency> {
}
