package com.challenge.children.service;

import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Lazy
public class ChildrenServiceHandler implements ChildrenService {

    @Autowired
    private ChildrenRepository repository;

    @Override
    public Optional<Children> findById(String id) {
        Optional<Children> item = repository.findById(id);
        return Optional.ofNullable(item.orElseGet(Children::new));
    }

    @Override
    public Children save(Children model) throws Exception {
        return repository.save(model);
    }

}
