package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class OrderQueryService {

    //여기에 로직을 몰아넣는 방향으로 트랜잭션 안에서 로직이 수행되게 함으로 지연 로딩을 가능하게 만들어준다.

}
