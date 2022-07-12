package hello.springtx.propagation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {


    @Id
    @GeneratedValue
    Long id;

    String username;

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
