package operation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageProcessor {
    private BufferedImage firstSource;
    private BufferedImage secondSource;
    private BufferedImage destination;

    private final Operation operation;
    private final int threads;

    public ImageProcessor(String firstPath, String secondPath, Operation operation, int threads) {
        this.threads = threads;
        this.operation = operation;
        try {
            firstSource = ImageIO.read(new File(firstPath));
            secondSource = ImageIO.read(new File(secondPath));

            int width = Math.min(firstSource.getWidth(), secondSource.getWidth());
            int height = Math.min(secondSource.getHeight(), firstSource.getHeight());

            destination = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void launchTasks() {
        int size = destination.getHeight();

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threads);
        for (int i = 0; i < threads; i++) {
            int start = (int) (i * (double)size / threads);
            int end = (int) Math.min((i + 1) * (double)size / threads, size);
            executorService.submit(new ApplyOperationTask(start, end, cyclicBarrier, firstSource, secondSource, destination, operation));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            ImageIO.write(destination, "png", new File("output.png"));
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
