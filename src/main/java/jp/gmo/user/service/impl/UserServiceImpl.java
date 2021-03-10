package jp.gmo.user.service.impl;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.UserDetailDto;
import jp.gmo.user.dto.request.LoginRequestDto;
import jp.gmo.user.dto.response.LoginResponseDto;
import jp.gmo.user.exception.CustomUsernameNotFoundException;
import jp.gmo.user.repository.UserRepository;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto executeAuthorize(LoginRequestDto request){

        try {
            LoginResponseDto response = new LoginResponseDto();
            // Verify email and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            AccountDto accountDto = userRepository.getAccountInfo(request.getEmail(), request.getPassword());
            UserDetailDto responseDto = new UserDetailDto();
            responseDto.setAccountDto(accountDto);

            response.setJwt(jwtTokenUtil.doGenerateToken(responseDto, true));
            return response;
        } catch (Exception e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            throw new CustomUsernameNotFoundException();
        }

    }
}
