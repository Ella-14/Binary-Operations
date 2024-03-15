package operation;

public class OrOperation implements Operation {
    @Override
    public int perform(int firstSource, int secondSource) {
        return firstSource | secondSource;
    }
}
