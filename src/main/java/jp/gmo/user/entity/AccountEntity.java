package jp.gmo.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private long id;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "employee_code")
    private String employeeCode;

    @NotNull
    @Size(max = 1)
    @Column(name = "role_id", length = 1, nullable = false)
    private Integer roleId;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
