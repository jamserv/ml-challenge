package com.challenge.api.commons;

import com.challenge.item.model.ItemBase;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
public interface MercadolibreService {

    @GET("/items/{item_id}")
    Call<ItemBase> findItemById(@Path("item_id") String item_id);

    @GET("/items/{item_id}/children")
    Call<List<ItemBase>> findChildrenItemByParentId(@Path("item_id") String item_id);
}
