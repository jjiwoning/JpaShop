package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void order(){
        //given
        Member member = createMember();

        Item book = createBook("클린코드", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        //주문 시 상태는 ORDER
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        //주문한 상품 종류의 수가 정확해야 된다.
        Assertions.assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        //주문 가격은 주문 수량 * 가격 이다.
        Assertions.assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        //주문 수량만큼 재고가 줄어야 한다.
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    @DisplayName("주문 취소")
    void cancel(){
        //given
        Member member = createMember();
        Item book = createBook("클린코드", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        //주문 취소시 상태는 cancel
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        //재고가 복구되어야 한다.
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("재고 수량 초과")
    void stockOver(){
        //given
        Member member = createMember();
        Item book = createBook("클린코드", 10000, 10);

        int orderCount = 11;

        //when
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount))
                        .isInstanceOf(NotEnoughStockException.class);

        //then
        //위에서 예외가 터져야 한다.
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("경기", "부천", "123-456"));
        em.persist(member);
        return member;
    }
}