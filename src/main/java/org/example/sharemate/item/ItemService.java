package org.example.sharemate.item;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, ItemDto itemDto);

    List<ItemDto> findAll();

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);
}
