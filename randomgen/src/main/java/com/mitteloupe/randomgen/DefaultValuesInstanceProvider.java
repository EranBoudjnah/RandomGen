package com.mitteloupe.randomgen;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

class DefaultValuesInstanceProvider<GENERATED_INSTANCE> implements RandomGen.InstanceProvider<GENERATED_INSTANCE> {
	private final Class<GENERATED_INSTANCE> mClass;

	DefaultValuesInstanceProvider(Class<GENERATED_INSTANCE> pClass) {
		mClass = pClass;
	}

	@Override
	public GENERATED_INSTANCE provideInstance() {
		try {
			return getInstanceFromAnyConstructor();

		} catch (Exception exception) {
			throw new InstanceCreationException(
				"Failed to instantiate " + mClass.getSimpleName() +
					". Try providing a ValuesInstanceProvider.",
				exception);
		}
	}

	private GENERATED_INSTANCE getInstanceFromAnyConstructor() throws Exception {
		List<Constructor<GENERATED_INSTANCE>> publicConstructors = getAllPublicConstructors();

		Constructor<GENERATED_INSTANCE> constructorToUse = null;

		while (constructorToUse == null && !publicConstructors.isEmpty()) {
			try {
				constructorToUse = getPreferredConstructor(publicConstructors);
				return getInstance(constructorToUse);

			} catch (Exception exception) {
				publicConstructors.remove(constructorToUse);
			}
		}

		return generateInstanceWithNewConstructor();
	}

	private List<Constructor<GENERATED_INSTANCE>> getAllPublicConstructors() {
		//noinspection unchecked We know what type to expect.
		Constructor<GENERATED_INSTANCE>[] constructors =
			(Constructor<GENERATED_INSTANCE>[]) mClass.getConstructors();

		return new ArrayList<>(Arrays.asList(constructors));
	}

	private GENERATED_INSTANCE getInstance(Constructor<GENERATED_INSTANCE> constructorToUse) throws Exception {
		if (constructorToUse == null) {
			return generateInstanceWithNewConstructor();

		} else {
			Class<?>[] parameterTypes = constructorToUse.getParameterTypes();
			Object[] parameterValues = getParameterValues(parameterTypes);

			return generatedInstanceWithParameters(constructorToUse, parameterValues);
		}
	}

	private Constructor<GENERATED_INSTANCE> getPreferredConstructor(List<Constructor<GENERATED_INSTANCE>> pConstructors) {
		Constructor<GENERATED_INSTANCE> constructorToUse = null;

		for (Constructor<GENERATED_INSTANCE> currentConstructor : pConstructors) {
			constructorToUse = currentConstructor;
			if (currentConstructor.getParameterTypes().length == 0) break;
		}

		return constructorToUse;
	}

	private Object[] getParameterValues(Class<?>[] pParameters) {
		Object[] parameterValues = new Object[pParameters.length];
		for (int i = 0; i < parameterValues.length; ++i) {
			Class<?> currentParameter = pParameters[i];
			if (currentParameter == short.class) {
				short defaultValue = 0;
				parameterValues[i] = defaultValue;

			} else if (currentParameter == int.class) {
				parameterValues[i] = 0;

			} else if (currentParameter == long.class) {
				parameterValues[i] = 0L;

			} else if (currentParameter == float.class) {
				parameterValues[i] = 0f;

			} else if (currentParameter == double.class) {
				parameterValues[i] = 0d;

			} else if (currentParameter == byte.class) {
				byte defaultResult = '\u0000';
				parameterValues[i] = defaultResult;

			} else if (currentParameter == boolean.class) {
				parameterValues[i] = false;

			} else {
				parameterValues[i] = null;
			}
		}
		return parameterValues;
	}

	private GENERATED_INSTANCE generatedInstanceWithParameters(Constructor<GENERATED_INSTANCE> constructor, Object... parameterValues) throws Exception {
		constructor.setAccessible(true);

		return constructor.newInstance(parameterValues);
	}

	private GENERATED_INSTANCE generateInstanceWithNewConstructor() throws Exception {
		try {
			return generateInstanceWithNewConstructorUsingReflectionFactory();

		} catch (Throwable throwable) {
			return generateInstanceWithNewConstructorUsingUnsafe();
		}
	}

	private GENERATED_INSTANCE generateInstanceWithNewConstructorUsingReflectionFactory() throws Exception {
		final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();
		//noinspection unchecked We know what type we expect generated.
		final Constructor<GENERATED_INSTANCE> constructor =
			(Constructor<GENERATED_INSTANCE>) reflection.newConstructorForSerialization(
				mClass, Object.class.getDeclaredConstructor());
		return constructor.newInstance();
	}

	private GENERATED_INSTANCE generateInstanceWithNewConstructorUsingUnsafe() throws Exception {
		Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
		unsafeConstructor.setAccessible(true);
		Unsafe unsafe = unsafeConstructor.newInstance();
		//noinspection unchecked We know what type we expect generated.
		return (GENERATED_INSTANCE) unsafe.allocateInstance(mClass);
	}

	private static class InstanceCreationException extends RuntimeException {
		private InstanceCreationException(String pMessage, Exception pException) {
			super(pMessage, pException);
		}
	}
}
