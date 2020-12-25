package org.kodluyoruz.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.txw2.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "login_tokens")
@NamedQuery(name = "LoginToken.findByValue",query = "select Lt from LoginToken Lt where Lt.value = ?1")
@NamedQuery(name = "LoginToken.findByUser",query = "select Lt from LoginToken Lt where Lt.user = ?1")
public class LoginToken extends MyEntity<Integer>{
    @Id
    @Column(name = "id",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value",nullable = false,unique = true,length = 36)
    private String value;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type",nullable = false)
    /*
    * Anonimse 0,logged inse 1 ekler EnumType.ORDINAL oldugu icin
    * */
    private Type type;//database de int olarak tuttugumuz icin enumerated anotasyonu kullandik

    @Column(name = "date_created",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();


    @Column(name = "expiration_duration",nullable = false)
    private Long expirationDuration = 0L;


    @Column(name = "date_logged_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLoggedOut = null;



    @ManyToOne(fetch = FetchType.EAGER)//Eager demek bu sinifi dbden elde ettigim zaman aninda user tablosuyla join et ve bu sinifi aninda setle
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user = null;

    @OneToMany(mappedBy = "loginToken",fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Link> links = new LinkedList<>();


    public enum Type
    {
        ANONYMOUS,
        LOGGED_IN
    }


    @JsonProperty("expired")
    @ToString.Include(name = "expired")
    public boolean isExpired()
    {
        Date expirationDate = new Date(dateCreated.getTime()+expirationDuration);
        return expirationDate.compareTo(new Date())<0;
    }

    @JsonProperty("logged out")
    @ToString.Include(name = "logged out")
    public boolean isLoggedOut()
    {
        return dateLoggedOut != null;
    }

    @JsonProperty("valid")
    @ToString.Include(name = "valid")
    public boolean isValid()
    {
        return !(isLoggedOut() || isExpired());
    }
}
