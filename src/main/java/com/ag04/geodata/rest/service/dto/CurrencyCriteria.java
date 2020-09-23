package com.ag04.geodata.rest.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ag04.geodata.rest.domain.Currency} entity. This class is used
 * in {@link com.ag04.geodata.rest.web.rest.CurrencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter numCode;

    private BooleanFilter preferred;

    public CurrencyCriteria() {
    }

    public CurrencyCriteria(CurrencyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.numCode = other.numCode == null ? null : other.numCode.copy();
        this.preferred = other.preferred == null ? null : other.preferred.copy();
    }

    @Override
    public CurrencyCriteria copy() {
        return new CurrencyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getNumCode() {
        return numCode;
    }

    public void setNumCode(StringFilter numCode) {
        this.numCode = numCode;
    }

    public BooleanFilter getPreferred() {
        return preferred;
    }

    public void setPreferred(BooleanFilter preferred) {
        this.preferred = preferred;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyCriteria that = (CurrencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(numCode, that.numCode) &&
            Objects.equals(preferred, that.preferred);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        numCode,
        preferred
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (numCode != null ? "numCode=" + numCode + ", " : "") +
                (preferred != null ? "preferred=" + preferred + ", " : "") +
            "}";
    }

}
