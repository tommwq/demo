public enum FaceValue {
        One, Two, Three, Four, Five, Six;

        public int toInteger() {
                return this.ordinal() + 1;
        }
}
