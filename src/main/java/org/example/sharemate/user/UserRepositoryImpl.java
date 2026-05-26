package org.example.sharemate.user;

import org.example.sharemate.exceptions.AlreadyExistException;
import org.example.sharemate.exceptions.NotFoundException;
import org.example.sharemate.exceptions.ValidationException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User save(User user) {
        validate(user);
        checkEmailExists(user.getEmail(), null);
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public User update(Long id, UserDto userDto) {
        User userToUpdate = findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (userDto.getName() != null) {
            userToUpdate.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            if (userDto.getEmail().isBlank() || !userDto.getEmail().contains("@")) {
                throw new ValidationException("Invalid email");
            }
            checkEmailExists(userDto.getEmail(), id);
            userToUpdate.setEmail(userDto.getEmail());
        }
        return userToUpdate;
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        users.remove(user);
    }

    @Override
    public void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !(user.getEmail().contains("@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    @Override
    public void checkEmailExists(String email, Long currentUserId) {
        for (User u : users) {
            if (currentUserId != null && Objects.equals(u.getId(), currentUserId)) {
                continue;
            }
            if (u.getEmail().equals(email)) {
                throw new AlreadyExistException("User with email " + email + " already exists");
            }
        }
    }
}
