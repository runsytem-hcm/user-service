package jp.gmo.user.service.impl;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.entity.AccountEntity;
import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.exception.EmailAlreadyUsedException;
import jp.gmo.user.exception.EmailNotExistException;
import jp.gmo.user.exception.InvalidPasswordException;
import jp.gmo.user.mapper.AccountMapper;
import jp.gmo.user.mapper.EmployeeMapper;
import jp.gmo.user.repository.AccountRepository;
import jp.gmo.user.repository.EmployeesRepository;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.RandomUtil;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;
    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDto> executeGetInfoAccount(String email) {
        return accountRepository.findByEmpEmail(email).map(accountMapper::toAccountDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeResetPassword(ResetPasswordRequest request) {

        AccountEntity account = accountRepository.findByEmpEmail(request.getEmail()).map(accountEntity -> {

            log.info("Old password for User: {}", accountEntity.getPassword());

            accountEntity.setPassword(bCryptPasswordEncoder.encode(RandomUtil.generatePassword()));
            accountEntity.setUpdateTime(LocalDateTime.now());
            accountEntity.setUpdateBy(Utils.getUpdateBy(request.getEmail()));

            return accountEntity;

        }).orElseThrow(EmailNotExistException::new);

        accountRepository.save(account);
        log.info("New password for User: {}", account.getPassword());

        // Send mail
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeChangePassword(String email, ChangePasswordRequest request) {

        AccountEntity account = accountRepository.findByEmpEmail(email).map(accountEntity -> {

            log.info("Old password for User: {}", accountEntity.getPassword());

            if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), accountEntity.getPassword())) {
                throw new InvalidPasswordException();
            }

            accountEntity.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            accountEntity.setUpdateTime(LocalDateTime.now());
            accountEntity.setUpdateBy(Utils.getUpdateBy(email));

            return accountEntity;

        }).orElseThrow(EmailNotExistException::new);

        accountRepository.save(account);
        log.info("New password for User: {}", account.getPassword());

        // Send mail
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeCreateAccount(CreateAccountRequest request) {

        AccountEntity account = new AccountEntity();

        employeesRepository.findByEmailAndDeleteFlag(request.getEmail(), 0).map(employeesEntity -> {

            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            account.setEmp(employeesEntity);
            account.setRoleId(Integer.valueOf(request.getRoleId()));
            account.setCreateBy(Utils.getUpdateBy(employeesEntity.getEmail()));
            account.setCreateTime(LocalDateTime.now());
            account.setDeleteFlag(0);

            return account;
        }).orElseThrow(EmailNotExistException::new);

        accountRepository.save(account);

        log.info("Created Information for Account: {}", account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeAddEmployees(AddEmployeesRequest request) {

        employeesRepository.findByEmailAndEmployeeCodeAndDeleteFlag(request.getEmail(), request.getEmployeeCode(), 0).ifPresent(em -> {
            throw new EmailAlreadyUsedException();
        });

        EmployeesEntity employeesEntity = new EmployeesEntity();
        employeesEntity.setEmployeeCode(request.getEmployeeCode());
        employeesEntity.setEmail(request.getEmail());
        employeesEntity.setEmployeeName(StringUtils.upperCase(request.getEmployeeName()));
        employeesEntity.setCreateBy(Utils.getUpdateBy(request.getEmail()));
        employeesEntity.setCreateTime(LocalDateTime.now());
        employeesEntity.setDeleteFlag(0);

        employeesRepository.save(employeesEntity);

        log.info("Created Information for employees: {}", employeesEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageAndDataResponseData<List<EmployeeDto>> executeGetListEmployees(String employeeName, String email, Pageable paging) {

        Page<EmployeesEntity> pageEmployees = employeesRepository.findByEmailContainingAndEmployeeNameContainingAndDeleteFlag(email, employeeName, 0, paging);
        List<EmployeeDto> employeeDtoList = pageEmployees.getContent().stream().map(employeeMapper::toEmployeesDto).collect(Collectors.toList());

        return PageAndDataResponseData.create(employeeDtoList, pageEmployees.getTotalElements(), paging.getPageNumber(), paging.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDto> executeGetDetailEmployee(String employeeCode) {
        return employeesRepository.findByEmployeeCodeAndDeleteFlag(employeeCode, 0).map(employeeMapper::toEmployeesDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeUpdateEmployees(UpdateEmployeesRequest request) {

        employeesRepository.findByEmployeeCodeAndDeleteFlag(request.getEmployeeCode(), 0).ifPresent(employee -> {

            employee.setEmployeeCode(request.getEmployeeCode());
            employee.setEmail(request.getEmail());
            employee.setEmployeeName(request.getEmployeeName());
            employee.setUpdateBy(Utils.getUpdateBy(request.getEmail()));
            employee.setUpdateTime(LocalDateTime.now());

            log.info("Changed Information for Employee: {}", employee);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> executeGetAllEmployees() {
        return employeesRepository.findByDeleteFlag(0)
                .stream()
                .map(employeeMapper::toEmployeesDto)
                .collect(Collectors.toList());
    }
}