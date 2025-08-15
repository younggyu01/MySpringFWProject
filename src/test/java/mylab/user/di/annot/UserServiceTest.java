package mylab.user.di.annot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:mylab-user-di.xml")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("UserService DI 및 기능 검증")
    void testUserService() {
        // 1) UserService 주입 확인
        assertNotNull(userService, "userService should be injected");

        // 2) UserRepository 주입 확인
        assertNotNull(userService.getUserRepository(), "userRepository should be injected");

        // 3) dbType 값 검증 ("MySQL")
        assertEquals("MySQL", userService.getUserRepository().getDbType(), "dbType should be MySQL");

        // 4) SecurityService 주입 확인
        assertNotNull(userService.getSecurityService(), "securityService should be injected");

        // 5) 기능 동작 검증: registerUser가 true인지
        boolean result = userService.registerUser("u100", "홍길동", "1234");
        assertTrue(result, "registerUser should return true");

        assertAll(
            () -> assertNotNull(userService),
            () -> assertNotNull(userService.getUserRepository()),
            () -> assertEquals("MySQL", userService.getUserRepository().getDbType()),
            () -> assertNotNull(userService.getSecurityService())
        );
    }
}
