package lyf;

import com.lyf.JavaSportApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JavaSportApplication.class)
public class JavaSportApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
     public void contextLoad(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}
