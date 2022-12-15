package Model.Value;

import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.IType;


public class BoolValue implements IValue {
        private boolean value;

        public BoolValue() {

            this.value = false;
        }

        public BoolValue(boolean value) {

            this.value = value;
        }

        @Override
        public IType getType() {

            return new BoolType();
        }

        @Override
        public String toString() {

            return Boolean.toString(value);
        }

        public boolean getValue() {

            return value;
        }

        @Override
        public boolean equals(Object another) {
            if (this == another)
                return true;
            if (another instanceof Model.Value.BoolValue) {
                return value == ((Model.Value.BoolValue) another).getValue();
            }
            return false;
        }

        //@Override
        public void setValue(IValue value) throws MyException {
            if (value instanceof Model.Value.BoolValue) {
                this.value = ((Model.Value.BoolValue) value).getValue();
            } else {
                throw new MyException("Invalid value");
            }
        }

        @Override
        public IValue deepCopy() {

            return new Model.Value.BoolValue(value);
        }
    }
