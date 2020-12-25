package org.kodluyoruz.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "links")
@NamedQuery(name = "Link.findByShortUrl",query = "select l from Link l where l.shortUrl = ?1")
@NamedQuery(name = "Link.findByUser",query = "select l from Link l where l.loginToken.user = ?1")
@NamedQuery(name = "Link.findByLoginToken",query = "select l from Link l where l.loginToken = ?1")
public class Link extends MyEntity<Integer>{
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_url",nullable = false,unique = true,length = 4)
    private String shortUrl;

    @Column(name = "date_created",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;


    @Column(name = "long_url",nullable = false,length = 2_083)
    private String longUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login_token_id",referencedColumnName = "id",nullable = false)
    private LoginToken loginToken;


    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "link",fetch = FetchType.LAZY)
    private List<Analytics> analyticsList = new LinkedList<>();
}
