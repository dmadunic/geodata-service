package com.ag04.geodata.rest.web.rest;

import com.ag04.geodata.rest.GeoDataServiceApp;
import com.ag04.geodata.rest.domain.Currency;
import com.ag04.geodata.rest.repository.CurrencyRepository;
import com.ag04.geodata.rest.service.CurrencyService;
import com.ag04.geodata.rest.service.dto.CurrencyDTO;
import com.ag04.geodata.rest.service.mapper.CurrencyMapper;
import com.ag04.geodata.rest.service.dto.CurrencyCriteria;
import com.ag04.geodata.rest.service.CurrencyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = GeoDataServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CurrencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CODE = "AAA";
    private static final String UPDATED_NUM_CODE = "BBB";

    private static final Boolean DEFAULT_PREFERRED = false;
    private static final Boolean UPDATED_PREFERRED = true;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .numCode(DEFAULT_NUM_CODE)
            .preferred(DEFAULT_PREFERRED);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .numCode(UPDATED_NUM_CODE)
            .preferred(UPDATED_PREFERRED);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();
        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCurrency.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCurrency.getNumCode()).isEqualTo(DEFAULT_NUM_CODE);
        assertThat(testCurrency.isPreferred()).isEqualTo(DEFAULT_PREFERRED);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setName(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setCode(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setNumCode(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setPreferred(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numCode").value(hasItem(DEFAULT_NUM_CODE)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.numCode").value(DEFAULT_NUM_CODE))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED.booleanValue()));
    }


    @Test
    @Transactional
    public void getCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        Long id = currency.getId();

        defaultCurrencyShouldBeFound("id.equals=" + id);
        defaultCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name equals to DEFAULT_NAME
        defaultCurrencyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the currencyList where name equals to UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name not equals to DEFAULT_NAME
        defaultCurrencyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the currencyList where name not equals to UPDATED_NAME
        defaultCurrencyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCurrencyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the currencyList where name equals to UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name is not null
        defaultCurrencyShouldBeFound("name.specified=true");

        // Get all the currencyList where name is null
        defaultCurrencyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name contains DEFAULT_NAME
        defaultCurrencyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the currencyList where name contains UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name does not contain DEFAULT_NAME
        defaultCurrencyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the currencyList where name does not contain UPDATED_NAME
        defaultCurrencyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code equals to DEFAULT_CODE
        defaultCurrencyShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the currencyList where code equals to UPDATED_CODE
        defaultCurrencyShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code not equals to DEFAULT_CODE
        defaultCurrencyShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the currencyList where code not equals to UPDATED_CODE
        defaultCurrencyShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCurrencyShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the currencyList where code equals to UPDATED_CODE
        defaultCurrencyShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code is not null
        defaultCurrencyShouldBeFound("code.specified=true");

        // Get all the currencyList where code is null
        defaultCurrencyShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code contains DEFAULT_CODE
        defaultCurrencyShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the currencyList where code contains UPDATED_CODE
        defaultCurrencyShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where code does not contain DEFAULT_CODE
        defaultCurrencyShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the currencyList where code does not contain UPDATED_CODE
        defaultCurrencyShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByNumCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode equals to DEFAULT_NUM_CODE
        defaultCurrencyShouldBeFound("numCode.equals=" + DEFAULT_NUM_CODE);

        // Get all the currencyList where numCode equals to UPDATED_NUM_CODE
        defaultCurrencyShouldNotBeFound("numCode.equals=" + UPDATED_NUM_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNumCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode not equals to DEFAULT_NUM_CODE
        defaultCurrencyShouldNotBeFound("numCode.notEquals=" + DEFAULT_NUM_CODE);

        // Get all the currencyList where numCode not equals to UPDATED_NUM_CODE
        defaultCurrencyShouldBeFound("numCode.notEquals=" + UPDATED_NUM_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNumCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode in DEFAULT_NUM_CODE or UPDATED_NUM_CODE
        defaultCurrencyShouldBeFound("numCode.in=" + DEFAULT_NUM_CODE + "," + UPDATED_NUM_CODE);

        // Get all the currencyList where numCode equals to UPDATED_NUM_CODE
        defaultCurrencyShouldNotBeFound("numCode.in=" + UPDATED_NUM_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNumCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode is not null
        defaultCurrencyShouldBeFound("numCode.specified=true");

        // Get all the currencyList where numCode is null
        defaultCurrencyShouldNotBeFound("numCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByNumCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode contains DEFAULT_NUM_CODE
        defaultCurrencyShouldBeFound("numCode.contains=" + DEFAULT_NUM_CODE);

        // Get all the currencyList where numCode contains UPDATED_NUM_CODE
        defaultCurrencyShouldNotBeFound("numCode.contains=" + UPDATED_NUM_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNumCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where numCode does not contain DEFAULT_NUM_CODE
        defaultCurrencyShouldNotBeFound("numCode.doesNotContain=" + DEFAULT_NUM_CODE);

        // Get all the currencyList where numCode does not contain UPDATED_NUM_CODE
        defaultCurrencyShouldBeFound("numCode.doesNotContain=" + UPDATED_NUM_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByPreferredIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where preferred equals to DEFAULT_PREFERRED
        defaultCurrencyShouldBeFound("preferred.equals=" + DEFAULT_PREFERRED);

        // Get all the currencyList where preferred equals to UPDATED_PREFERRED
        defaultCurrencyShouldNotBeFound("preferred.equals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByPreferredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where preferred not equals to DEFAULT_PREFERRED
        defaultCurrencyShouldNotBeFound("preferred.notEquals=" + DEFAULT_PREFERRED);

        // Get all the currencyList where preferred not equals to UPDATED_PREFERRED
        defaultCurrencyShouldBeFound("preferred.notEquals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByPreferredIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where preferred in DEFAULT_PREFERRED or UPDATED_PREFERRED
        defaultCurrencyShouldBeFound("preferred.in=" + DEFAULT_PREFERRED + "," + UPDATED_PREFERRED);

        // Get all the currencyList where preferred equals to UPDATED_PREFERRED
        defaultCurrencyShouldNotBeFound("preferred.in=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByPreferredIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where preferred is not null
        defaultCurrencyShouldBeFound("preferred.specified=true");

        // Get all the currencyList where preferred is null
        defaultCurrencyShouldNotBeFound("preferred.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numCode").value(hasItem(DEFAULT_NUM_CODE)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .numCode(UPDATED_NUM_CODE)
            .preferred(UPDATED_PREFERRED);
        CurrencyDTO currencyDTO = currencyMapper.toDto(updatedCurrency);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCurrency.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCurrency.getNumCode()).isEqualTo(UPDATED_NUM_CODE);
        assertThat(testCurrency.isPreferred()).isEqualTo(UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
