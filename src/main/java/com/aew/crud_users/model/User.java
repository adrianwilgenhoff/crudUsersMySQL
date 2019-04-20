package com.aew.crud_users.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

/**
 * Represents all the user’s information.
 * 
 * @author Adrian E. Wilgenhoff
 * @version 1.0
 * 
 */

@Entity
@Table(name = "Users")
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated user ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username", unique = true)
    @ApiModelProperty(notes = "The username of the User", required = true)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password", nullable = false)
    @ApiModelProperty(notes = "The password for loggin of the User", required = true)
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    @ApiModelProperty(notes = "The name of the User", required = true)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastname")
    @ApiModelProperty(notes = "The lastname of the User", required = true)
    private String lastname;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "address")
    @ApiModelProperty(notes = "The address of the User", required = true)
    private String address;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "city")
    @ApiModelProperty(notes = "The city of the User", required = true)
    private String city;

    @Email
    @NotNull
    @Size(min = 6, max = 50)
    @Column(name = "email", unique = true)
    @ApiModelProperty(notes = "The email of the User", required = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "telephone", unique = true)
    @ApiModelProperty(notes = "The telephone number of the User", required = true)
    private String telephone;

    @NotNull
    // @Size(min = 1, max = 50)
    @Column(name = "dni")
    @ApiModelProperty(notes = "The dni of the User", required = true)
    private long dni;

    public User() {
    }

    /**
     * Construye un Usuario con sus datos personales.
     * 
     * @param username  valor utilizado para configurar el campo "username" del
     *                  usuario.
     * @param password  valor utilizado para configurar la contraseña de loggin del
     *                  usuario.
     * @param name      valor utilizado para configurar el nombre real del usuario.
     * @param lastname  valor utilizado para configurar el apellido real del
     *                  usuario..
     * @param address   valor utilizado para configurar la direcccion donde vive el
     *                  usuario.
     * @param city      valor utilizado para configurar la ciudad de residencia del
     *                  usuario.
     * @param email     valor utilizado para configurar el correo electronico del
     *                  usuario.
     * @param telephone valor utilizado para configurar el numero de telefono del
     *                  usuario.
     * @param dni       valor utilizado para configurar el dni del usuario.
     */

    public User(Long id, String username, String password, String name, String lastname, String address, String city,
            String email, String telephone, Long dni) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.email = email;
        this.telephone = telephone;
        this.dni = dni;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getDni() {
        return this.dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password)
                && Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname)
                && Objects.equals(address, user.address) && Objects.equals(city, user.city)
                && Objects.equals(email, user.email) && Objects.equals(telephone, user.telephone) && dni == user.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, lastname, address, city, email, telephone, dni);
    }

    @Override
    public String toString() {
        return "{" + " username='" + getUsername() + "'" + ", password='" + getPassword() + "'" + ", name='" + getName()
                + "'" + ", lastname='" + getLastname() + "'" + ", address='" + getAddress() + "'" + ", city='"
                + getCity() + "'" + ", email='" + getEmail() + "'" + ", telephone='" + getTelephone() + "'" + ", dni='"
                + getDni() + "'" + "}";
    }

}
