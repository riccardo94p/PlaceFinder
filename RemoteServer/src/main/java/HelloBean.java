public class HelloBean implements Hello {

    private int howManyTimes = 0;

    public int howManyTimes() {
        return howManyTimes;
    }

    public String getHelloWorld() {
        howManyTimes++;
        return "Hello World! "+howManyTimes+" calls.";
    }

}
