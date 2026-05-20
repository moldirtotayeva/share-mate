package org.example.sharemate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserDtoMapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream().map((u)-> {
            UserDto userDto = mapper.toDto(u);
            return userDto;
        }).toList();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        return mapper.toDto(repository.save(user));
    }
}
