package org.pascal2spim.language.types;

import java.util.Vector;

public class ArrayType extends Type {
    Type elsType;
    Vector dimensions = new Vector();

    public ArrayType() {
    }

    public Type getElsType() {
        return elsType;
    }

    public void setElsType(Type elsType) {
        this.elsType = elsType;
    }

    public boolean setDimensions(int n, int min, int max) {
        boolean done = false;
        if (min <= max) {
            if (dimensions.size() < (n + 1) * 2)
                dimensions.setSize((n + 1) * 2);
            dimensions.setElementAt(new Integer(min), n * 2);
            dimensions.setElementAt(new Integer(max), n * 2 + 1);
        }
        return done;
    }

    public int getNumberOfDimensions() {
        return dimensions.size() / 2;
    }

    public boolean hasDimension(int n) {
        return (n * 2 < dimensions.size());
    }

    public int getDimensionMin(int n) {
        Integer integ = (Integer) dimensions.elementAt(n * 2);
        return integ.intValue();
    }

    public int getDimensionMax(int n) {
        Integer integ = (Integer) dimensions.elementAt(n * 2 + 1);
        return integ.intValue();
    }

    public boolean isEqualTo(Type other) {
        return this.isCompatibleWith(other);
    }

    public boolean isCompatibleWith(Type other) {
        boolean compatible = false;
        if (other instanceof ArrayType) {
            ArrayType at = (ArrayType) other;
            boolean correctDims = true;
            int i = 0;

            while (i * 2 < dimensions.size() && correctDims) {
                correctDims = false;
                if (at.hasDimension(i))
                    if (this.getDimensionMin(i) == at.getDimensionMin(i))
                        if (this.getDimensionMax(i) == at.getDimensionMax(i))
                            correctDims = true;
                i++;
            }
            if (correctDims && at.hasDimension(i * 2))
                correctDims = false;
            if (correctDims && elsType.isEqualTo(at.getElsType()))
                compatible = true;
        }
        return compatible;
    }

    public boolean isIndexable() {
        return true;
    }

    public String toString() {
        String result = "array[";
        for (int i = 0; i < this.getNumberOfDimensions(); i++) {
            result = result + this.getDimensionMin(i) + ".." + this.getDimensionMax(i) + ", ";
        }
        return result.substring(0, result.length() - 2) + "] of " + elsType;
    }

    public int getTotalNumberOfElements() {
        int n = 1;
        for (int i = 0; i < this.getNumberOfDimensions(); i++) {
            n = n * (this.getDimensionMax(i) - this.getDimensionMin(i) + 1);
        }
        if (elsType instanceof ArrayType)
            n = n * ((ArrayType) elsType).getTotalNumberOfElements();
        return n;
    }

    public Type getFinalElsType() {
        if (elsType instanceof ArrayType)
            return ((ArrayType) elsType).getFinalElsType();
        else
            return elsType;
    }
}