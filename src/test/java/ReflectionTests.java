import xyz.yooniks.proxy.user.Optionable;

public class ReflectionTests {

  //private String text = "jakisText";
  private Optionable<String> someOptionable = new Optionable<>("lool");

  public static void main(String... args) throws Exception {
    final ReflectionTests tests = new ReflectionTests();

    final Optionable<String> optionable = (Optionable<String>) tests.getClass()
        .getDeclaredField("someOptionable")
        .get(tests);
    System.out.println(optionable.getValue());
    /*final Field field = tests.getClass().getDeclaredField("text");
    field.setAccessible(true);

    System.out.println(field.get(tests));

    field.set(tests, "jakisText12312");
    System.out.println(field.get(tests)); */
  }

}
