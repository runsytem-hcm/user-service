package jp.gmo.user.service;

import jp.gmo.user.dto.request.LoginRequestDto;
import jp.gmo.user.dto.response.LoginResponseDto;

public interface UserService {
    LoginResponseDto executeAuthorize(LoginRequestDto loginRequestDto);
}
