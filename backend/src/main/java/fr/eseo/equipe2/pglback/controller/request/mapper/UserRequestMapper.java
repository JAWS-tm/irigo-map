package fr.eseo.equipe2.pglback.controller.request.mapper;

import fr.eseo.equipe2.pglback.controller.request.LoginRequest;
import fr.eseo.equipe2.pglback.controller.request.RegisterRequest;
import fr.eseo.equipe2.pglback.dto.UserDto;

public class UserRequestMapper {
    public static UserDto toUserDto(RegisterRequest registerRequest) {
        return new UserDto()
                .setEmail(registerRequest.getEmail())
                .setPassword(registerRequest.getPassword())
                .setFirstName(registerRequest.getFirstName())
                .setLastName(registerRequest.getLastName())
                .setSex(registerRequest.getSex())
                .setBirthday(registerRequest.getBirthday())
                .setTravelHabits(registerRequest.getTravelHabits())
                .setTravelFrequency(registerRequest.getTravelFrequency());
    }

    public static UserDto toUserDto(LoginRequest loginRequest) {
        return new UserDto()
                .setEmail(loginRequest.getEmail())
                .setPassword(loginRequest.getPassword());
    }
}
