# RandomGen
> Initialize instances of any class with generated data.

## Install

In your `build.gradle`, add the following:

```groovy
dependencies {
	implementation project(':randomgen')
}
```

To include the default data generators, also include
```groovy
implementation project(':randomgen.datasource')
```

## Usage

### Java
```java
RandomGen<ObjectClass> randomGen = new RandomGen.Builder<>(new RandomGen.InstanceProvider<ObjectClass>() {
	@Override
	public ObjectClass provideInstance() {
		return new ObjectClass();
	}
})
	.withField("id")
	.returningSequentialInteger()
	.withField("uuid")
	.returningUuid()
	.build();
```

### Kotlin
```kotlin
val randomGen = RandomGen.Builder(RandomGen.InstanceProvider { ObjectClass() })
	.withField("id")
	.returningSequentialInteger()
	.withField("uuid")
	.returningUuid()
	.build()
```

This will create a `RandomGen` instance producing `ObjectClass` instances with sequential IDs and random UUIDs.

To use the newly generated `RandomGen`, simply call:

```java
ObjectClass instance = randomGen.generate();
```

```kotlin
val instance = randomGen.generate()
```
