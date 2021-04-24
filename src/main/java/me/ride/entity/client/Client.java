package me.ride.entity.client;

import lombok.Getter;
import lombok.Setter;
import me.ride.entity.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_client")
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 40, message = "Фамилия не короче 2 и не длинее 40 символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$", message = "Только буквы")
    private String surname;

    @Size(min = 1, max = 30, message = "Имя не короче 2 и не длинее 30 символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$", message = "Только буквы")
    private String name;

    @Email(message = "Невозможный адрес")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Невозможная дата")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Size(max = 90, message = "Не длинее 90 символов")
    private String passportNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Невозможная дата")
    private Date dateOfIssue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Срок действия истёк")
    private Date dateOfExpiry;

    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$", message = "Только буквы")
    private String authority;

    @Size(max = 90, message = "Не длинее 90 символов")
    private String personalNo;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public Client() {
    }

    public Client(String surname, String name, String email,
                  Date dateOfBirth, Sex sex, String passportNo,
                  Date dateOfIssue, Date dateOfExpiry,
                  String authority, String personalNo) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.passportNo = passportNo;
        this.dateOfIssue = dateOfIssue;
        this.dateOfExpiry = dateOfExpiry;
        this.authority = authority;
        this.personalNo = personalNo;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}