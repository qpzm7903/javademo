import java.lang.instrument.Instrumentation;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-10-21-22:15
 */
public class SimpleAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        final SimpleClassTransformer transformer = new SimpleClassTransformer();
        inst.addTransformer(transformer);
    }
}