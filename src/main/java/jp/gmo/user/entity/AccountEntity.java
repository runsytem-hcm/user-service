package jp.gmo.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "delete_flag")
    private int deleteFlag;

    @OneToOne()
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "employee_code")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EmployeesEntity emp;
}
