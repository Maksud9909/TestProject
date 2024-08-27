package org.example.testproject.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@NoArgsConstructor
@Embeddable
public class FullName {
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MIDDLENAME")
    private String middleName;
    @JsonCreator
    public FullName(@JsonProperty("firstName") final String firstName,
                    @JsonProperty("lastName") final String lastName,
                    @JsonProperty("middleName") final String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FullName fullName = (FullName) o;
        return Objects.equal(firstName, fullName.firstName) &&
                Objects.equal(lastName, fullName.lastName) &&
                Objects.equal(middleName, fullName.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName, middleName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("middleName", middleName)
                .toString();
    }
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }
}

