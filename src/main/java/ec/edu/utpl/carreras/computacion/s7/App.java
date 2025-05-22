package ec.edu.utpl.carreras.computacion.s7;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import ec.edu.utpl.carreras.computacion.s7.tasks.TaskSummarize;

import java.util.concurrent.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        var task = new TaskSummarize("/Users/monky02/Desktop/PROGRAMACION AVANZADA/weatherengine/weatherHistory_1.csv");
      //  var thread = new Thread(task);
        Future<ClimateSummary> future = executor.submit(task);

//        thread.start();
//        thread.join();
        var result = future.get();
        System.out.println(result);
        executor.shutdown();

//        if(  executor.awaitTermination(10, TimeUnit.SECONDS) ){
//            System.out.println(task.getResult());
//        }else {
//            System.out.println("Timeout");
//        }





    }
}
