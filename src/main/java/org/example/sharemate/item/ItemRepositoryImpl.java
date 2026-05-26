package org.example.sharemate.item;

import lombok.RequiredArgsConstructor;
import org.example.sharemate.exceptions.NotFoundException;
import org.example.sharemate.exceptions.ValidationException;
import org.example.sharemate.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    HashMap<Long, Item> items = new HashMap<>();
    private final UserRepository userRepository;
    private Long nextId = 1L;

    @Override
    public Item findById(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Item with id " + id + " not found");
        }
        return items.get(id);
    }

    @Override
    public Item save(Long userId, Item item) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        validate(item);
        item.setUserId(userId);
        item.setId(nextId++);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> findAll(Long userId) {
        return items.values().stream().filter((i) -> i.getUserId().equals(userId)).toList();
    }

    @Override
    public Item update(Long userId, Long itemId, ItemDto itemDto) {
        Item itemToUpdate = items.get(itemId);
        if (!Objects.equals(itemToUpdate.getUserId(), userId)) {
            throw new NotFoundException("Item not found");
        }
        if (itemDto.getName() != null) {
            itemToUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            itemToUpdate.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            itemToUpdate.setAvailable(itemDto.getAvailable());
        }
        return itemToUpdate;
    }

    @Override
    public void validate(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException("Item not available");
        }
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new ValidationException("Item name not be empty");
        }
        if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            throw new ValidationException("Item description not be empty");
        }
    }

    @Override
    public List<Item> findByNameOrDescription(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        String query = text.toLowerCase();
        List<Item> result = items.values().stream()
                .filter(Item::getAvailable)
                .filter((i) -> i.getName().toLowerCase().contains(query) || i.getDescription().toLowerCase().contains(query))
                .toList();
        return result;
    }
}
