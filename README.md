# RandomGen
>
> Initialize instances of any class with generated data.
>
[![Version - RandomGen](https://img.shields.io/bintray/v/shadowcra/RandomGen/RandomGen?label=randomgen+|+bintray)](https://bintray.com/shadowcra/RandomGen/RandomGen)
[![Version - DataSource](https://img.shields.io/bintray/v/shadowcra/RandomGen/RandomGen.DataSource?label=datasourcee+|+bintray)](https://bintray.com/shadowcra/RandomGen/RandomGen.DataSource)

[![Build Status](https://travis-ci.com/EranBoudjnah/RandomGen.svg?branch=master)](https://travis-ci.com/EranBoudjnah/RandomGen)
[![License](https://img.shields.io/github/license/EranBoudjnah/Solid)](https://github.com/EranBoudjnah/RandomGen/blob/master/LICENSE)

![Example](https://github.com/EranBoudjnah/RandomGen/raw/master/example/videocap.gif)

Inspired by [Cesar Ferreira](https://cesarferreira.com)'s [RxPeople](https://github.com/cesarferreira/RxPeople), I thought I'd implement an abstraction of the idea, so you could generate random instances of any class.

This is great for demoing your app with interesting content, manually testing it with varying data, and even populating it with smart, random generated data in production.

## Install

In your `build.gradle`, add the following:

```groovy
dependencies {
	implementation 'com.mitteloupe:randomgen:1.4.1'
}
```

To include the default data generators, also include
```groovy
dependencies {
	implementation 'com.mitteloupe:randomgen.datasource:1.0.1'
}
```

Note: To add the BinTray repository in your maven repositories, also add the following:
```groovy
repositories {
	maven {
		url "https://dl.bintray.com/shadowcra/RandomGen"
	}
}
```


## Usage

### Java
```java
RandomGen<ObjectClass> randomGen = new RandomGen.Builder<ObjectClass>()
	.ofClass(ObjectClass.class)
	.withField("id")
	.returningSequentialInteger()
	.withField("uuid")
	.returningUuid()
	.build();
```

### Kotlin
```kotlin
val randomGen = RandomGen.Builder<ObjectClass>()
	.ofClass(ObjectClass::class.java)
	.withField("id")
	.returningSequentialInteger()
	.withField("uuid")
	.returningUuid()
	.build()
```

This will create a `RandomGen` instance producing `ObjectClass` instances with sequential IDs and random UUIDs.

To use the newly generated `RandomGen`, simply call:

### Java
```java
ObjectClass instance = randomGen.generate();
```

### Kotlin
```kotlin
val instance = randomGen.generate()
```

## Created by
[Eran Boudjnah](https://www.linkedin.com/in/eranboudjnah)

## License
MIT © [Eran Boudjnah](https://www.linkedin.com/in/eranboudjnah)
