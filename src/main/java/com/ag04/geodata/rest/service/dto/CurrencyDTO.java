package com.ag04.geodata.rest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ag04.geodata.rest.domain.Currency} entity.
 */
public class CurrencyDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 3)
    private String numCode;

    @NotNull
    private Boolean preferred;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public Boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyDTO)) {
            return false;
        }

        return id != null && id.equals(((CurrencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", numCode='" + getNumCode() + "'" +
            ", preferred='" + isPreferred() + "'" +
            "}";
    }
}
