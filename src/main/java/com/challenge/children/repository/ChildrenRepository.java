package com.challenge.children.repository;

import com.challenge.children.model.Children;
import com.challenge.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
public interface ChildrenRepository extends JpaRepository<Children, String> {

    List<Children> findByItem(Item item);

}
