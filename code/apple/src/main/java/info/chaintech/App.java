package info.chaintech;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException, IOException {
//        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " World!").join();
//        System.out.println(result);
//
//        CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
//
//        String result2 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "s1";
//        }).applyToEither(CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "hello world";
//        }), s -> s).join();
//        System.out.println(result2);

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            int i = 1/0;
//            return 100;
//        });
//        // future.join();
//        future.get();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(App::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(f.get());
        System.in.read();
    }

    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();

    private static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }
}
