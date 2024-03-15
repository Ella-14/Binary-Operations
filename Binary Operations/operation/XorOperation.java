package operation;

public class XorOperation implements Operation {
    @Override
    public int perform(int firstSource, int secondSource) {
        return firstSource ^ secondSource;
    }
}
