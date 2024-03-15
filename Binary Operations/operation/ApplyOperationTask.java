package operation;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ApplyOperationTask extends ProcessingTask {
    private final BufferedImage firstSource;
    private final BufferedImage secondSource;
    private final BufferedImage outputSource;

    private final Operation operation;

    public ApplyOperationTask(int start, int end, CyclicBarrier cyclicBarrier, BufferedImage firstSource, BufferedImage secondSource, BufferedImage outputSource, Operation operation) {
        super(start, end, cyclicBarrier);
        this.firstSource = firstSource;
        this.secondSource = secondSource;
        this.outputSource = outputSource;
        this.operation = operation;
    }

    @Override
    public void run() {
        try {
            System.out.printf("[%s] is waiting - %s\n", Thread.currentThread().getName(), Instant.now());
            getCyclicBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        for (int y = getStart(); y < getEnd(); y++) {
            for (int x = 0; x < outputSource.getWidth(); x++) {
                int pixel = operation.perform(firstSource.getRGB(x, y), secondSource.getRGB(x, y));
                outputSource.setRGB(x, y, pixel);
            }
        }

        try {
            getCyclicBarrier().await();
            Thread.sleep(1000);
            System.out.printf("[%s] has finished at %s\n", Thread.currentThread().getName(), Instant.now());
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
