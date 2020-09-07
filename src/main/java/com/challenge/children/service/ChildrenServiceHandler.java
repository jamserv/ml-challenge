package com.challenge.children.service;

import com.challenge.api.commons.ChallengeException;
import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
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
    public Children save(Children model) throws ChallengeException {
        return repository.save(model);
    }

}
