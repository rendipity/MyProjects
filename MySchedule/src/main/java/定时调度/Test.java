package 定时调度;

class Test {
    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        Thread p1 = new Thread(producer::start, "producer");
        Thread c1 = new Thread(consumer::start, "consumer");
        try {
            p1.start();
            c1.start();
            p1.join();
            c1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
