package jp.gmo.user.service.impl;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.entity.AccountEntity;
import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;
import jp.gmo.user.exception.EmailAlreadyUsedException;
import jp.gmo.user.exception.EmailNotExistException;
import jp.gmo.user.exception.InvalidPasswordException;
import jp.gmo.user.mapper.EmployeeMapper;
import jp.gmo.user.repository.AccountRepository;
import jp.gmo.user.repository.EmployeesRepository;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.RandomUtil;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AccountRepository accountRepository;
    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDto> executeGetInfoAccount(String email) {
        return accountRepository.getAccountInfo(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeResetPassword(ResetPasswordRequest request) {

        AccountDto accountDto = accountRepository.getAccountInfo(request.getEmail()).map(AccountDto::new).orElseThrow(EmailNotExistException::new);
        String newPassword = RandomUtil.generatePassword();
        accountRepository.updatePassword(accountDto.getEmail(), bCryptPasswordEncoder.encode(newPassword),
                LocalDateTime.now(), Utils.getUpdateBy(request.getEmail()));
        log.debug("Reset password for User: {}", newPassword);
        // Send mail
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeChangePassword(String email, ChangePasswordRequest request) {

        AccountDto accountDto = accountRepository.getAccountInfo(email).map(acc -> {
            if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), acc.getPassword())) {
                throw new InvalidPasswordException();
            }
            return new AccountDto(acc);
        }).orElseThrow(EmailNotExistException::new);

        String newPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        log.debug("Old password: {}", request.getCurrentPassword());

        accountRepository.updatePassword(email, newPassword, LocalDateTime.now(), Utils.getUpdateBy(email));
        log.debug("New password: {}", newPassword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeCreateAccount(CreateAccountRequest request) {

        EmployeeDto employees = employeesRepository.findByEmail(request.getEmail()).map(employeeMapper::toEmployeesDto).orElseThrow(EmailNotExistException::new);

        AccountEntity account = new AccountEntity();

        account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        account.setEmployeeCode(employees.getEmployeeCode());
        account.setRoleId(Integer.valueOf(request.getRoleId()));
        account.setCreateBy(Utils.getUpdateBy(employees.getEmail()));
        account.setCreateTime(LocalDateTime.now());
        account.setDeleteFlag(0);

        accountRepository.saveAndFlush(account);

        log.debug("Created Information for Account: {}", account);

    }

    @Override
    public void executeAddEmployees(AddEmployeesRequest request) {

        EmployeesKey key = new EmployeesKey();
        key.setEmail(request.getEmail());
        key.setEmployeeCode(request.getEmployeeCode());

        employeesRepository.findByEmployees(request.getEmail(), request.getEmployeeCode()).ifPresent(em -> {
            throw new EmailAlreadyUsedException();
        });

        EmployeesEntity employeesEntity = new EmployeesEntity();
        employeesEntity.setId(key);
        employeesEntity.setEmployeeName(StringUtils.upperCase(request.getEmployeeName()));
        employeesEntity.setCreateBy(Utils.getUpdateBy(request.getEmail()));
        employeesEntity.setCreateTime(LocalDateTime.now());
        employeesEntity.setDeleteFlag(0);

        employeesRepository.saveAndFlush(employeesEntity);

        log.debug("Created Information for employees: {}", employeesEntity);
    }

    @Override
    public PageAndDataResponseData<List<EmployeeDto>> executeGetListEmployees(SearchEmployeesRequest request) {

        int limit = Integer.parseInt(request.getTotalRecordOfPage());
        int offset = (Integer.parseInt(request.getCurrentPage()) - 1) * limit;

        List<EmployeeDto> employeeDtoList = employeesRepository
                .getListEmployees(request.getEmail(), request.getEmployeeName(), offset, limit)
                .stream()
                .map(employeeMapper::toEmployeesDto)
                .collect(Collectors.toList());

        BigInteger totalRecord = employeesRepository.countEmployees(request.getEmail(), request.getEmployeeName());

        return PageAndDataResponseData.create(employeeDtoList, request.getCurrentPage(), request.getCurrentPage(), String.valueOf(totalRecord));
    }
}
