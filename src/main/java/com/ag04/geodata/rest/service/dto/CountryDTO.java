package com.ag04.geodata.rest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ag04.geodata.rest.domain.Country} entity.
 */
public class CountryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 2)
    private String codeA2;

    @NotNull
    @Size(max = 3)
    private String codeA3;

    @Size(max = 255)
    private String flag;

    @NotNull
    private Boolean active;

    
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

    public String getCodeA2() {
        return codeA2;
    }

    public void setCodeA2(String codeA2) {
        this.codeA2 = codeA2;
    }

    public String getCodeA3() {
        return codeA3;
    }

    public void setCodeA3(String codeA3) {
        this.codeA3 = codeA3;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        return id != null && id.equals(((CountryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", codeA2='" + getCodeA2() + "'" +
            ", codeA3='" + getCodeA3() + "'" +
            ", flag='" + getFlag() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
