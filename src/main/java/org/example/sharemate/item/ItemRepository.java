package org.example.sharemate.item;

import java.util.List;

public interface ItemRepository {
    Item findById(Long id);

    Item save(Long userId, Item item);

    List<Item> findAll();

    Item update(Long userId, Long itemId, ItemDto itemDto);

    void validate(Item item);
}
