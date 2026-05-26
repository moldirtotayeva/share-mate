package org.example.sharemate.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemDtoMapper mapper;

    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {
        Item item = mapper.toItem(itemDto);
        return mapper.toDto(itemRepository.save(userId, item));
    }

    @Override
    public List<ItemDto> findAll() {
        itemRepository.findAll();
        return List.of();
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.update(userId, itemId, itemDto);
        return mapper.toDto(item);
    }
}
