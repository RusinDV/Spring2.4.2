package web.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String password;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @OrderBy("authgroup")
    private List<AuthGroup> authGroupList;

    public User() {
    }

    public User(String name, String lastName, int age, String password, List<AuthGroup> authGroupList) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.authGroupList = authGroupList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthGroup> getAuthGroupList() {
        return authGroupList;
    }

    public void setAuthGroupList(List<AuthGroup> authGroupList) {
        if (this.authGroupList == null) {
            this.authGroupList = authGroupList;
        } else {
            this.authGroupList.retainAll(authGroupList);
            this.authGroupList.addAll(authGroupList);
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", authGroupList=" + authGroupList +
                '}';
    }
}
