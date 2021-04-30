package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_refuse_note")
public class RefuseNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    private String message;

    public RefuseNote() {
    }

    public RefuseNote(Order order, String message) {
        this.order = order;
        this.message = message;
    }
}
