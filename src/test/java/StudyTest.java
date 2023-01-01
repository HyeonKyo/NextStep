import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class StudyTest {
    @Test
    void bufferedReader() throws IOException {
        System.setIn(new ByteArrayInputStream("asd\nasd\n\nasd".getBytes()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 2; i++) {
            String line = br.readLine();
            assertThat(line).isEqualTo("asd");
        }

        String line = br.readLine();
        System.out.println(line);
        assertThat(line).isEqualTo("");
    }

    @Test
    void string_format_test() {
        Object value = true;
        String s = String.format("%s=%s", "asd", value.toString());
        System.out.println(s);
    }

    @Test
    void booleanParse() {
        boolean b = Boolean.parseBoolean(null);
        System.out.println(b);
    }
}
