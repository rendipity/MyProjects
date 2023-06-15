package 定时调度;

public class ScheduleX {

    private Node[] nodes;
    private int size;

    private static volatile ScheduleX scheduleSingleton;

    // 创建一个长度为n的空堆
    private ScheduleX(int n) {
        this.nodes = new Node[n];
        this.size=0;
    }
    // 懒汉式实现单例
    public static ScheduleX getInstance(){
        if (scheduleSingleton==null){
            synchronized (ScheduleX.class){
                if (scheduleSingleton==null)
                    scheduleSingleton=new ScheduleX(10);
            }
        }
        return scheduleSingleton;
    }

    // 向下调整
    private void childAdjust(int index){
        int leftIndex=2*index+1;
        if (leftIndex>=this.size)
            return;
        Node[] nums=this.nodes;
        int rightIndex=2*index+2;
        int minChildIndex=rightIndex<this.size&&nums[leftIndex].time>=nums[rightIndex].time?rightIndex:leftIndex;
        if (nums[minChildIndex].time<nums[index].time){
            swap(index,minChildIndex);
            childAdjust(minChildIndex);
        }
    }
    // 交换元素
    private void swap(int index1,int index2){
        Node t = this.nodes[index1];
        this.nodes[index1] = this.nodes[index2];
        this.nodes[index2] = t;
    }
    // 向上调整
    private void parentAdjust(int index){
        if (index==0)
            return ;
        int parentIndex = (index-1)/2;
        if (this.nodes[parentIndex].time>this.nodes[index].time){
            swap(parentIndex,index);
            parentAdjust(parentIndex);
        }
    }
    // 扩容2倍
    private void resize(){
        Node[] newNodes=new Node[this.nodes.length<<1];
        System.arraycopy(this.nodes,0,newNodes,0,this.nodes.length);
        this.nodes=newNodes;
    }
    // 移除堆顶元素
    private Node remove(){
        Node result=this.nodes[0];
        swap(0,--this.size);
        childAdjust(0);
        return result;
    }
    // 查看堆顶元素的剩余延迟时间
    private long getDelay(){
        // 生产者在第一次注册任务时认为堆顶的距离执行时间为无限大，则需要唤醒之前进入无限期等待的线程
        if (this.size <= 0 )
            return Long.MAX_VALUE;
        Node result=this.nodes[0];
        return result.time-System.currentTimeMillis();
    }

    // 消费者阻塞式获取任务
    // 当堆内没有任务时进入无限时等待
    // 堆内有任务时查看堆顶的任务是否到底调度时间，如果到了则执行任务，如果没到则线程wait(距离调度剩余时间)
    public synchronized Node get() throws InterruptedException {
        while(true){
            if (this.size<=0)
                wait();
            long delay ;
            if ((delay=getDelay())<=0){
                return remove();
            }
            wait(delay);
        }
    }
    // 生产者增加调度任务
    public synchronized void register(long time,String task){
        // 当堆满时则扩容
        if (this.size>= nodes.length)
            resize();
        Node[] nums=this.nodes;
        // 计算当前线程距离调度的时间，如果小于堆顶元素的时间则需要唤醒线程计算休眠时间
        long currentDelay=System.currentTimeMillis()-time;
        long delay = getDelay();
        nums[this.size++]=new Node(time,task);
        parentAdjust(this.size-1);
        if (currentDelay<delay)
            // 当前线程距离调度的时间小于堆顶元素的时间唤醒线程计算休眠时间
            notifyAll();
    }
    // 任务节点
    class Node{
        long time;
        String task;

        public Node(long time, String task) {
            this.time = time;
            this.task = task;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "time=" + time +
                    ", task='" + task + '\'' +
                    '}';
        }
    }
}
