import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import org.junit.Test;

public class ReflectionTests {

  private String text = "jakisText";

  @Test
  public void reflectionTest() throws Exception {
    final String fieldValue = this.text;

    final ReflectionTests tests = new ReflectionTests();
    final Field field = tests.getClass().getDeclaredField("text");
    field.setAccessible(true);

    field.set(tests, "jakisText12312");
    assertEquals(field.get(tests), fieldValue);
  }

}
