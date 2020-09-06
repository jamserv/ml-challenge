package com.challenge.children.service;

import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class ChildrenServiceHandler implements ChildrenService {

    @Autowired
    private ChildrenRepository repository;

    @Override
    public Children save(Children model) throws Exception {
        return repository.save(model);
    }

    @Override
    public Optional<Children> findById(String id) {
        Optional<Children> item = repository.findById(id);
        return Optional.ofNullable(item.orElseGet(Children::new));
    }

    @Override
    public Boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public List<Children> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

}
