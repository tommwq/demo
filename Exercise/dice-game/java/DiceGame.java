public class DiceGame {
        private Dice dice1 = new Dice();
        private Dice dice2 = new Dice();

        private final static int WIN_SUM = 7;

        public void startGame() {
                dice1.roll();
                showFaceValue(dice1.getFaceValue());

                dice2.roll();
                showFaceValue(dice2.getFaceValue());

                showGameResult();
        }

        public boolean isPlayerWin() {
                return dice1.getFaceValue().toInteger() + dice2.getFaceValue().toInteger() == WIN_SUM;
        }

        private void showFaceValue(FaceValue faceValue) {
                System.out.println("Face value: " + faceValue.toInteger());
        }

        private void showGameResult() {
                if (isPlayerWin()) {
                        System.out.println("win");
                } else {
                        System.out.println("loss");
                }
        }
}
