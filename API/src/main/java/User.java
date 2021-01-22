import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    private Integer id;

    private String username;
    private String password;
    private Integer privilege;
    private boolean covidNotification;
}