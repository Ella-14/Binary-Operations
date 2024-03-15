package operation;

public class AndOperation implements Operation {
    @Override
    public int perform(int firstSource, int secondSource) {
        return firstSource & secondSource;
    }
}
