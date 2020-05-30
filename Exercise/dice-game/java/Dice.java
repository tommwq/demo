import java.util.Random;

public class Dice {
        private FaceValue faceValue = FaceValue.One;
        
        public void roll() {
                faceValue = FaceValue.values()[new Random().nextInt(FaceValue.values().length)];
        }

        public FaceValue getFaceValue() {
                return faceValue;
        }
}
