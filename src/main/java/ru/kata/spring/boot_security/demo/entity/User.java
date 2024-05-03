package ru.kata.spring.boot_security.demo.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Size(min = 2, max = 100, message = "Name must be min 2 and max 100 symbols")
    @NotBlank(message = "Name is required field")
    @Pattern(regexp = "[A-Za-z]+", message = "Name must consist only letters")
    private String name;
    @Column
    @Size(min = 2, max = 150, message = "Lastname must be min 2 and max 150 symbols")
    @NotBlank(message = "Lastname is required field")
    @Pattern(regexp = "[A-Za-z]+", message = "Lastname must consist only letters")
    private String lastname;
    @Column
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 149, message = "Age must be less than 150")
    private byte age;

    public User() {}

    public User(String name, String lastname, byte age) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                '}';
    }
}
