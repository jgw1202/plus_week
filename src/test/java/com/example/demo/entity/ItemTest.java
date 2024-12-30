package com.example.demo.entity;

import com.example.demo.config.JPAConfiguration;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
public class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testStatusCannotBeNull() {
        User owner = new User("admin", "asdf@naver.com", "OwnerUserNickname", "testpass");
        User manager = new User("user", "asdfuser@naver.com", "ManagerNickname", "testpass");
        userRepository.save(owner);
        userRepository.save(manager);

//        Item item = new Item("ItemName", "ItemDescription", manager, owner);

        // Item 엔티티 status에 @NotNull 추가 후 테스트 가능
//        assertThrows(ConstraintViolationException.class,
//                () -> itemRepository.saveAndFlush(item),
//                "status 값이 null로 저장되는 Item은 ConstraintViolationException을 발생시킵니다.");

        assertThrows(PersistenceException.class, () -> {
            entityManager.createNativeQuery(
                    "INSERT INTO item (name, description, owner_id, manager_id, status) VALUES ('ItemName', 'ItemDescription', 1, 2, NULL)"
            ).executeUpdate();
        });

    }
}
