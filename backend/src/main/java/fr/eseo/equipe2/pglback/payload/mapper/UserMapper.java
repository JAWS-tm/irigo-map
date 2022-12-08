package fr.eseo.equipe2.pglback.payload.mapper;

import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.model.User;


public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setSex(user.getSex())
                .setBirthday(user.getBirthday())
                .setTravelHabits(user.getTravelHabits())
                .setTravelFrequency(user.getTravelFrequency());
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User()
                .setEmail(userDto.getEmail())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setBirthday(userDto.getBirthday())
                .setSex(userDto.getSex())
                .setTravelFrequency(userDto.getTravelFrequency())
                .setTravelHabits(userDto.getTravelHabits());
        return user;
    }

}
