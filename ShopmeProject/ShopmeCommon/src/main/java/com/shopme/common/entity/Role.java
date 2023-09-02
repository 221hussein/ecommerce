package com.shopme.common.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 40,nullable = false,unique = true)
    private String name;
    @Column(length = 150,nullable = false)
    private String descriptions;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    public Role(String name, String descriptions) {
        this.name = name;
        this.descriptions = descriptions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
