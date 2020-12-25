package org.kodluyoruz.urlshortener.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "analytics")
public class Analytics extends MyEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER)//Eager demek bu sinifi dbden elde ettigim zaman aninda user tablosuyla join et ve bu sinifi aninda setle
    @JoinColumn(name = "link_id",referencedColumnName = "id")
    private Link link = null;

    @Column(name = "date_created",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "ip",nullable = false,length = 15)
    private String ip;

    @Column(name = "continent_name",nullable = false)
    private String continentName;

    @Column(name = "country_name",nullable = false)
    private String countryName;

    @Column(name = "country_code",nullable = false)
    private String countryCode;

    @Column(name = "city_name",nullable = false)
    private String cityName;


    @Column(name = "os_name",nullable = false)
    private String operatingSystemName;

    @Column(name = "os_code",nullable = false)
    private String operatingSystemCode;

    @Column(name = "browser_name",nullable = false)
    private String browserName;

    @Column(name = "browser_version",nullable = false)
    private String browserVersion;

    @Column(name = "browser_major_version",nullable = false)
    private String browserMajorVersion;

    @Column(name = "browser_engine",nullable = false)
    private String browserEngine;

    @Column(name = "user_agent",nullable = false)
    private String userAgent;
}
