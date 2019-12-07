package com.mitteloupe.randomgen;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DefaultValuesInstanceProviderTest {
    private DefaultValuesInstanceProvider<?> mCut;

    @Test
    public void givenConstructorWithParametersWhenProvideInstanceThenReturnsInstanceWithDefaultValues() {
        // Given
        mCut = new DefaultValuesInstanceProvider<>(TestClassWithFields.class);

        // When
        TestClassWithFields result = (TestClassWithFields) mCut.provideInstance();

        // Then
        assertEquals(0, result.getByteField());
        assertEquals(0, result.getShortField());
        assertEquals(0, result.getIntField());
        assertEquals(0, result.getLongField());
        assertEquals(0, result.getFloatField(), 0f);
        assertEquals(0, result.getDoubleField(), 0d);
        assertFalse(result.isBooleanField());
        assertNull(result.getStringField());
        assertNull(result.getObject());
        assertNull(result.getObjects());
    }

    @Test
    public void givenNoDefinedConstructorWhenProvideInstanceThenReturnsInstance() {
        // Given
        mCut = new DefaultValuesInstanceProvider<>(TestClassWithNoConstructor.class);

        // When
        TestClassWithNoConstructor result = (TestClassWithNoConstructor) mCut.provideInstance();

        // Then
        assertNotNull(result);
    }

    @Test
    public void givenPublicConstructorWithNoParametersWhenProvideInstanceThenUsesConstructor() {
        // Given
        mCut = new DefaultValuesInstanceProvider<>(TestClassWithNoFields.class);

        // When
        TestClassWithNoFields result = (TestClassWithNoFields) mCut.provideInstance();

        // Then
        assertTrue(result.isSet());
    }

    private interface TestClass {
    }

    private static class TestClassWithNoConstructor implements TestClass {
    }

    private static class TestClassWithNoFields implements TestClass {
        private final boolean mSet;

        TestClassWithNoFields() {
            mSet = true;
        }

        boolean isSet() {
            return mSet;
        }
    }

    private static class TestClassWithFields implements TestClass {
        private final byte mByteField;
        private final short mShortField;
        private final int mIntField;
        private final long mLongField;
        private final float mFloatField;
        private final double mDoubleField;
        private final boolean mBooleanField;
        private final String mStringField;
        private final Object mObject;
        private final List<Object> mObjects;

        TestClassWithFields(byte pByteField, short pShortField, int pIntField,
                            long pLongField, float pFloatField, double pDoubleField,
                            boolean pBooleanField, String pStringField, Object pObject,
                            List<Object> pObjects) {
            mShortField = pShortField;
            mIntField = pIntField;
            mLongField = pLongField;
            mFloatField = pFloatField;
            mDoubleField = pDoubleField;
            mBooleanField = pBooleanField;
            mByteField = pByteField;
            mStringField = pStringField;
            mObject = pObject;
            mObjects = pObjects;
        }

        byte getByteField() {
            return mByteField;
        }

        short getShortField() {
            return mShortField;
        }

        int getIntField() {
            return mIntField;
        }

        long getLongField() {
            return mLongField;
        }

        float getFloatField() {
            return mFloatField;
        }

        double getDoubleField() {
            return mDoubleField;
        }

        boolean isBooleanField() {
            return mBooleanField;
        }

        String getStringField() {
            return mStringField;
        }

        Object getObject() {
            return mObject;
        }

        List<Object> getObjects() {
            return mObjects;
        }
    }
}