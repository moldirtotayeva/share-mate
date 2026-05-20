package org.example.sharemate.user;

import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }

    public User toUser(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(user.getName());
        return user;
    }
}
