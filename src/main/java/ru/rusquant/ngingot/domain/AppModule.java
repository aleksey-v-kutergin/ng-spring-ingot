package ru.rusquant.ngingot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_modules")
public class AppModule {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ident")
    private String ident;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "parent")
    private Long parent;

    @Column(name = "ord")
    private Integer ord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }
}
