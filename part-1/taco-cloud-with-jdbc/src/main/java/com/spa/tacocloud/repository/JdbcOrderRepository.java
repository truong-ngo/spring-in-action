package com.spa.tacocloud.repository;

import aj.org.objectweb.asm.Type;
import com.spa.tacocloud.model.IngredientRef;
import com.spa.tacocloud.model.Taco;
import com.spa.tacocloud.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory ps = new PreparedStatementCreatorFactory("""
                insert into Taco_order 
                (delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at)
                values (?,?,?,?,?,?,?,?,?)""",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        ps.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());
        PreparedStatementCreator pc = ps.newPreparedStatementCreator(Arrays.asList(
                order.getDeliveryName(),
                order.getDeliveryStreet(),
                order.getDeliveryCity(),
                order.getDeliveryState(),
                order.getDeliveryZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV(),
                order.getPlacedAt()
        ));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(pc, keyHolder);
        Long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        AtomicInteger i = new AtomicInteger(0);
        tacos.forEach(taco -> saveTaco(orderId, i.getAndIncrement(), taco));
        return order;
    }

    private void saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory ps = new PreparedStatementCreatorFactory("""
                insert into Taco
                (name, created_at, taco_order, taco_order_key)
                values (?, ?, ?, ?)""",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
        ps.setReturnGeneratedKeys(true);
        PreparedStatementCreator pc = ps.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey)
        );
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(pc, keyHolder);
        Long tacoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
    }

    private void saveIngredientRefs(Long tacoId, List<IngredientRef> ingredientRefs) {
        AtomicInteger key = new AtomicInteger(0);
        ingredientRefs.forEach(i -> jdbcOperations.update(
                "insert into Ingredient_Ref (ingredient, taco, taco_key) values (?, ?, ?)",
                i.getIngredient(), tacoId, key.getAndIncrement()));
    }
}
