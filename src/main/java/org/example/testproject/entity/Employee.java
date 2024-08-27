package org.example.testproject.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Embedded
    private FullName fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "POSITION")
    private Position position;

    @Column(name = "HIREDATE")
    private LocalDate hiredate;

    @Column(name = "SALARY")
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "MANAGER")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT")
    private Department department;

    @JsonCreator
    public Employee(@JsonProperty("id") final Long id,
                    @JsonProperty("fullName") final FullName fullName,
                    @JsonProperty("position") final Position position,
                    @JsonProperty("hired") final LocalDate hiredate,
                    @JsonProperty("salary") final BigDecimal salary,
                    @JsonProperty("manager") final Employee manager,
                    @JsonProperty("department") final Department department) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.hiredate = hiredate;
        this.salary = salary.setScale(5, RoundingMode.HALF_UP);
        this.manager = manager;
        this.department = department;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Employee employee = (Employee) o;
        return Objects.equal(id, employee.id) &&
                Objects.equal(fullName, employee.fullName) &&
                position == employee.position &&
                Objects.equal(hiredate, employee.hiredate) &&
                Objects.equal(salary, employee.salary) &&
                Objects.equal(manager, employee.manager) &&
                Objects.equal(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, fullName, position, hiredate, salary, manager, department);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("fullName", fullName)
                .add("position", position)
                .add("hired", hiredate)
                .add("salary", salary)
                .add("manager", manager)
                .add("department", department)
                .toString();
    }

    public static class Parser {
        private static ObjectMapper mapper = new ObjectMapper();

        static {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        }

        public static String toJson(Employee employee) {
            try {
                final StringWriter writer = new StringWriter();
                mapper.writeValue(writer, employee);
                return writer.toString();
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        public static Employee parseJson(String json) {
            try {
                return mapper.readValue(json, Employee.class);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}
