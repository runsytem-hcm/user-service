package jp.gmo.user.service.impl;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.entity.AccountEntity;
import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;
import jp.gmo.user.exception.EmailNotExistException;
import jp.gmo.user.exception.EmailOrCodeExistException;
import jp.gmo.user.exception.InsertDataException;
import jp.gmo.user.exception.UpdatePasswordException;
import jp.gmo.user.mapper.EmployeeMapper;
import jp.gmo.user.repository.AccountRepository;
import jp.gmo.user.repository.EmployeesRepository;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.AccountResponseData;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.RandomUtil;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AccountRepository accountRepository;
    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Override
    @Transactional(readOnly = true)
    public AccountResponseData executeGetInfoAccount(LoginRequest request) {

        AccountResponseData response = new AccountResponseData();
        try {
            AccountDto accountDto = accountRepository.getAccountInfo(request.getEmail());
            if (accountDto != null) {
                BeanUtils.copyProperties(accountDto, response);
            }
            return response;
        } catch (Exception ex) {
            LOG.error("{0}", ex);
            return response;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeResetPassword(ResetPasswordRequest request) {

        AccountDto accountDto = accountRepository.getAccountInfo(request.getEmail());
        if (accountDto == null) {
            throw new EmailNotExistException();
        }

        try {
            String newPassword = RandomUtil.generatePassword();
            accountRepository.updatePassword(request.getEmail(), bCryptPasswordEncoder.encode(newPassword),
                    LocalDateTime.now(), Utils.getUpdateBy(request.getEmail()));
            // Send mail
        } catch (Exception ex) {
            LOG.error("{0}", ex);
            throw new UpdatePasswordException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeChangePassword(String email, ChangePasswordRequest request) {

        AccountDto accountDto = accountRepository.getAccountInfo(email);
        if (accountDto == null) {
            throw new EmailNotExistException();
        }

        try {
            accountRepository.updatePassword(email, bCryptPasswordEncoder.encode(request.getNewPassword()),
                    LocalDateTime.now(), Utils.getUpdateBy(email));
        } catch (Exception ex) {
            LOG.error("{0}", ex);
            throw new UpdatePasswordException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeCreateAccount(CreateAccountRequest request) {

        EmployeesEntity employees = employeesRepository.findByEmail(request.getEmail());

        if (employees == null) {
            throw new EmailNotExistException();
        }

        try {

            AccountEntity account = new AccountEntity();

            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            account.setEmployeeCode(employees.getId().getEmployeeCode());
            account.setRoleId(Integer.valueOf(request.getRoleId()));
            account.setCreateBy(Utils.getUpdateBy(employees.getId().getEmail()));
            account.setCreateTime(LocalDateTime.now());
            account.setDeleteFlag(0);

            accountRepository.saveAndFlush(account);

        } catch (Exception ex) {
            LOG.error("{0}", ex);
            throw new InsertDataException();
        }
    }

    @Override
    public void executeAddEmployees(AddEmployeesRequest request) {

        EmployeesKey key = new EmployeesKey();
        key.setEmail(request.getEmail());
        key.setEmployeeCode(request.getEmployeeCode());

        EmployeesEntity employeesOptional = employeesRepository.findByEmployees(request.getEmail(), request.getEmployeeCode());

        if (employeesOptional != null) {
            throw new EmailOrCodeExistException();
        }

        try {
            EmployeesEntity employeesEntity = new EmployeesEntity();
            employeesEntity.setId(key);
            employeesEntity.setEmployeeName(StringUtils.upperCase(request.getEmployeeName()));
            employeesEntity.setCreateBy(Utils.getUpdateBy(request.getEmail()));
            employeesEntity.setCreateTime(LocalDateTime.now());
            employeesEntity.setDeleteFlag(0);

            employeesRepository.saveAndFlush(employeesEntity);
        } catch (Exception ex) {
            LOG.error("{0}", ex);
            throw new InsertDataException();
        }
    }

    @Override
    public PageAndDataResponseData executeGetListEmployees(SearchEmployeesRequest request) {

        PageAndDataResponseData response = new PageAndDataResponseData();
        try {

            int limit = Integer.parseInt(request.getTotalRecordOfPage());
            int offset = (Integer.parseInt(request.getCurrentPage()) - 1) * limit;
            List<EmployeeDto> employeeDtoList = new ArrayList<>();

            for (EmployeesEntity entity : employeesRepository.getListEmployees(request.getEmail(), request.getEmployeeName(), offset, limit)) {
                employeeDtoList.add(employeeMapper.toEmployeesDto(entity));
            }

            BigInteger totalRecord = employeesRepository.countEmployees(request.getEmail(), request.getEmployeeName());

            response.setList(employeeDtoList);
            response.setCurrentPage(request.getCurrentPage());
            response.setTotalRecordOfPage(request.getCurrentPage());
            response.setTotalRecord(String.valueOf(totalRecord));

            return response;
        } catch (Exception ex) {
            LOG.error("{0}", ex);
            return response;
        }

    }
}
