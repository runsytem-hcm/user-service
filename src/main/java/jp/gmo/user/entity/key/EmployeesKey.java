package jp.gmo.user.entity.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Embeddable
@Data
public class EmployeesKey implements Serializable {

	private static final long serialVersionUID = 6662604929149505812L;

	@NotNull
    @Column(name = "employee_code")
    private String employeeCode;

    @NotNull
    @Column(name = "email")
    private String email;
}
