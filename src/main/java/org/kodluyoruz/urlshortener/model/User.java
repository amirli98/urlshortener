package org.kodluyoruz.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.kodluyoruz.urlshortener.util.Constants;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findByEmail",query = "select u from User u where u.email = ?1")
public class User extends MyEntity<Integer>{

    @Id
    @Column(name = "id",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email",nullable = false,unique = true,length = 320)
    private String email;

    @Column(name = "password",nullable = false,length = 60)
    @JsonIgnore //JSON olarak dondugumuzde gozukmez
    @XmlTransient //XML olarak dondugumuzde gozukmez
    @ToString.Exclude // String olarak dondugumuzde gozukmez
    private String password;

    @Column(name = "date_created",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<LoginToken> loginTokens = new LinkedList<>();


    public void encryptAndSetPassword(String password)
    {
        this.password = Constants
                .getEncoder()
                .encode(password);
    }

    public boolean checkPassword(String password)
    {
        return Constants
                .getEncoder()
                .matches(password,this.password);
    }
}
