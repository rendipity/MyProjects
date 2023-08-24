import org.junit.Test;
import reactor.core.publisher.Flux;

public class ReactTest {


    @Test
    public void fluxTest(){
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);
        just.subscribe(System.out::println);
    }
    
    @Test
    public void bufferTest(){
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);
        //just.buffer(3).subscribe(System.out::println);
        //just.bufferUntil(i->i%2==0).subscribe(System.out::println);
        just.bufferWhile(i->i<3).buffer(2).subscribe(System.out::println);
    }
    @Test
    public void zipWithTest(){
        Flux.just("a", "b",'c').zipWith(Flux.just("c", "d")).subscribe(System.out::println);
    }
    @Test
    public void takeTest(){
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
    }
    @Test
    public void reduceTest(){
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
    }
    @Test
    public void mergeTest(){
        Flux.merge(Flux.range(0, 100).take(5), Flux.range(50, 100).take(5)).toStream().forEach(System.out::println);
    }


}
