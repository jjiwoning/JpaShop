package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            // 신규 등록
            em.persist(item);
        } else{
            // 이미 등록됨. 이 경우 update 라고 보면 된다.
            em.merge(item);
        }
    }

    public Item findOne(Long itemId){
        return em.find(Item.class, itemId);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
