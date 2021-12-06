import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-10-21-22:02
 */
public class SimpleClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(
            final ClassLoader loader,
            final String className,
            final Class<?> classBeingRedefined,
            final ProtectionDomain protectionDomain,
            final byte[] classfileBuffer) {
        if (className.endsWith("sun/net/www/protocol/http/HttpURLConnection")) {
            try {
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz =
                        classPool.get("sun.net.www.protocol.http.HttpURLConnection");
                for (final CtConstructor constructor : clazz.getConstructors()) {
                    constructor.insertAfter("System.out.println(this.getURL());");
                }
                byte[] byteCode = clazz.toBytecode();
                clazz.detach();
                return byteCode;
            } catch (final NotFoundException | CannotCompileException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}