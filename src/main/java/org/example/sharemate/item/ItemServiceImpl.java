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
    public List<ItemDto> findAll(Long userId) {
        return itemRepository.findAll(userId).stream().map(mapper::toDto).toList();
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.update(userId, itemId, itemDto);
        return mapper.toDto(item);
    }

    @Override
    public ItemDto findById(Long id) {
        ItemDto itemDto = mapper.toDto(itemRepository.findById(id));
        return itemDto;
    }

    @Override
    public List<ItemDto> findByNameOrDescription(String text) {
        return itemRepository.findByNameOrDescription(text).stream().map(mapper::toDto).toList();
    }
}
