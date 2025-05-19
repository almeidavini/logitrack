package br.com.mercadoenvios.logitrack.infrastructure.config;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * <p>Offers transformation capabilities for records, transforming an (immutable)
 * record fluidly into a new record instance in a typesafe way.</p>
 *
 * <b>Example:</b>
 * <pre>
 *  {@code new Person("James", "Gosling", 0).with(Person::age, 65));
 * // Person[firstName=James, lastName=Gosling, age=65]}
 * </pre>
 *
 * @author Sebastian Teumert
 *
 * @param <R> the implementing record itself
 */
public interface CopyableRecord<R extends Record & CopyableRecord<R>> {

    private static boolean isCompatible(RecordComponent component, String name,
                                        String typeDescriptor) {
        return component.getName().equals(name) && component.getAccessor()
                .getReturnType().descriptorString().equals(typeDescriptor);
    }

    /**
     * c.f. https://stackoverflow.com/a/35223119/1360803
     */
    private static SerializedLambda getSerializedLambda(Serializable lambda)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        final Method method = lambda.getClass()
                .getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        return (SerializedLambda) method.invoke(lambda);
    }

    /**
     * c.f. https://stackoverflow.com/a/35223119/1360803
     */
    @Deprecated
    @SuppressWarnings({ "rawtypes", "unused" })
    private static Method getReflectedMethod(Serializable lambda)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ClassNotFoundException {
        SerializedLambda s = getSerializedLambda(lambda);
        Class containingClass = Class.forName(s.getImplClass());
        String methodName = s.getImplMethodName();
        for (Method m : containingClass.getDeclaredMethods()) {
            if (m.getName().equals(methodName))
                return m;
        }

        throw new NoSuchElementException("reflected method could not be found");
    }

    /**
     * <p>
     * Create a copy of this record, replacing exactly one field with another
     * value. Can be chained to change multiple fields.
     * </p>
     *
     * <p>
     * The given method reference must be the accessor method for the field, the
     * given value will replace the old value of that field in the copy.
     * </p>
     *
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     *  {@code new Person("James", "Gosling", 0).with(Person::age, 65));
     * // Person[firstName=James, lastName=Gosling, age=65]}
     * </pre>
     * </p>
     *
     * @param <T> Type of the field to replace
     * @param <F> Type of the accessor method
     * @param param Accessor method of the field to replace
     * @param val Value the field should have in the copy
     * @return copy of the record, with the field replaced with the given value
     */
    @SuppressWarnings("unchecked")
    public default <T, F extends Serializable & Function<R, T>> R with(F param,
                                                                       T val) {
        try {
            // get name & type of the changing parameter
            var lambda = getSerializedLambda(param);
            var name = lambda.getImplMethodName();
            var signature = lambda.getImplMethodSignature();
            // get descriptor, strip () of input
            var typeDescriptor = signature.substring(2, signature.length());

            // get record components & replace the value
            var components = getClass().getRecordComponents();
            var params = new Object[components.length];
            for (int i = 0; i < components.length; i++) {
                var component = components[i];
                if (isCompatible(component, name, typeDescriptor))
                    params[i] = val;
                else {
                    params[i] = component.getAccessor().invoke(this);
                    // accessor might modify data, so circumvent accessor
                    // but records don't expose their fields :(
                    //params[i] = getClass().getField(component.getName()).get(this);
                }
            }
            // create new record
            return (R) getClass().getConstructors()[0].newInstance(params);
        } catch (NoSuchMethodException | SecurityException
                 | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } /*catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}*/
    }
}
