package org.example.sharemate.user;

import lombok.RequiredArgsConstructor;
import org.example.sharemate.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserDtoMapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        UserDto newUser = mapper.toDto(repository.save(user));
        return newUser;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User updated = repository.update(id, userDto);
        return mapper.toDto(updated);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return mapper.toDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }
}
