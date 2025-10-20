package com.template.service.ipml;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import lombok.AllArgsConstructor;

import com.template.repository.AppUserRepository;
import com.template.service.AppUserService;
import com.template.domain.entity.AppUser;
import com.template.domain.mapper.AppUserMapper;
import com.template.domain.dto.AppUserRequestDto;
import com.template.domain.dto.AppUserResponseDto;

import jakarta.persistence.EntityNotFoundException;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final AppUserMapper mapper;

    @Override
    public AppUserResponseDto findById(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppUser não encontrado: " + id));
        return mapper.toResponseDto(user);
    }

    @Override
    public Page<AppUserResponseDto> findAll(AppUserRequestDto exampleDto, Pageable pageable) {
        AppUser objExample = mapper.toEntity(exampleDto);
        Example<AppUser> example = Example.of(objExample, getMatcher());

        Page<AppUser> users = userRepository.findAll(example, pageable);
        return users.map(mapper::toResponseDto);
    }

    /**
     * Configures the ExampleMatcher for dynamic filtering.
     * <p>
     * This matcher enables:
     * <ul>
     *   <li>Case-insensitive string matching</li>
     *   <li>Partial string matching (CONTAINING)</li>
     *   <li>Null values are ignored by default</li>
     * </ul>
     *
     * @return ExampleMatcher configured for flexible query-by-example searches
     */
    private ExampleMatcher getMatcher() {
        return ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    }

    public AppUserResponseDto create(AppUserRequestDto requestDto){
        AppUser newObj = mapper.toEntity(requestDto);
        newObj = userRepository.save(newObj);
        return mapper.toResponseDto(newObj);
    }

    @Override
    public AppUserResponseDto update(Long id, AppUserRequestDto requestDto) {

        AppUser existingObj = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppUser não encontrado: " + id));
        mapper.updateEntityFromDto(requestDto, existingObj);

        AppUser updatedObj = userRepository.save(existingObj);
        return mapper.toResponseDto(updatedObj);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("AppUser não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }

}
