package tyrinov.sergey.contactbook.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(schema = "public", name="contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_id_seq")
    @SequenceGenerator(name = "contacts_id_seq", schema = "public", sequenceName = "contacts_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;
}
