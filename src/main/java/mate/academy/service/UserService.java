package mate.academy.service;

import mate.academy.dto.UserRegistrationRequestDto;
import mate.academy.dto.UserResponseDto;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto request);
}
