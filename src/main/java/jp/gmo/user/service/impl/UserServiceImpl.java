package jp.gmo.user.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.entity.AccountEntity;
import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;
import jp.gmo.user.exception.EmailNotExistException;
import jp.gmo.user.exception.InsertDataException;
import jp.gmo.user.exception.UpdatePasswordException;
import jp.gmo.user.repository.AccountRepository;
import jp.gmo.user.repository.EmployeesRepository;
import jp.gmo.user.request.ChangePasswordRequest;
import jp.gmo.user.request.CreateAccountRequest;
import jp.gmo.user.request.LoginRequest;
import jp.gmo.user.request.ResetPasswordRequest;
import jp.gmo.user.response.data.AccountResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.RandomUtil;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AccountRepository accountRepository;
    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        EmployeesKey key = new EmployeesKey();
        key.setEmail(request.getEmail());

        EmployeesEntity entity = employeesRepository.findByEmail(request.getEmail());

        if (entity == null) {
            throw new EmailNotExistException();
        }

        try {

            AccountEntity account = new AccountEntity();

            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            account.setEmployeeCode(entity.getId().getEmployeeCode());
            account.setRoleId(Integer.valueOf(request.getRoleId()));
            account.setCreateBy(Utils.getUpdateBy(entity.getId().getEmail()));
            account.setCreateTime(LocalDateTime.now());
            account.setDeleteFlag(0);

            accountRepository.saveAndFlush(account);

        } catch (Exception ex) {
            LOG.error("{0}", ex);
            throw new InsertDataException();
        }
    }
}
