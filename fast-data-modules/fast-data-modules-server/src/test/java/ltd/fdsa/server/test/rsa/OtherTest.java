package ltd.fdsa.server.test.rsa;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class OtherTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {

        String path1 = "/sdf/sdfsf/sfsdf";
        String[] f = path1.split("/");
        for (String sss : f) {
            log.debug(sss);
        }
    }
}
