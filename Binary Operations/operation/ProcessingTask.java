package operation;

import java.util.concurrent.CyclicBarrier;

public abstract class ProcessingTask implements Runnable {
    private final int start;
    private final int end;
    private final CyclicBarrier cyclicBarrier;

    public ProcessingTask(int start, int end, CyclicBarrier cyclicBarrier) {
        this.start = start;
        this.end = end;
        this.cyclicBarrier = cyclicBarrier;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }
}
