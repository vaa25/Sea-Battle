package networks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alexander Vlasov
 */
public class ObjectParser {
    private ConcurrentHashMap<Class, BlockingQueue> map;

    public ObjectParser() {
        this.map = new ConcurrentHashMap<>();
    }

    public Object take(Class clazz) throws InterruptedException {
        Object object = getQueue(clazz).take();
        System.out.println(Thread.currentThread().getName() + " ObjectParser успешно take " + object);
        return object;
    }

    private BlockingQueue getQueue(Class clazz) {
        BlockingQueue queue = new LinkedBlockingQueue();
        BlockingQueue oldQueue = map.putIfAbsent(clazz, queue);
        return oldQueue == null ? queue : oldQueue;
    }

    public void put(Object object) throws InterruptedException {
        Class clazz = object.getClass();
        getQueue(clazz).add(object);
        System.out.println(Thread.currentThread().getName() + " ObjectParser успешно put " + object);
    }

//    public static void main(String[] args) throws InterruptedException {
//        ObjectParser objectParser = new ObjectParser();
//        Message message = new Message("blablabla");
//        Coord coord = new Coord(2, 4);
//        objectParser.put(message);
//        objectParser.put(coord);
//
//
//        new Thread(new First(objectParser)).start();
//        new Thread(new Second(objectParser)).start();
//    }
//
//    static class First implements Runnable {
//        private ObjectParser objectParser;
//
//        First(ObjectParser objectParser) {
//            this.objectParser = objectParser;
//        }
//
//        @Override
//        public void run() {
//            Coord newCoord = null;
//            try {
//                newCoord = (Coord) objectParser.take(Coord.class);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(newCoord);
//        }
//    }
//
//    static class Second implements Runnable {
//        private ObjectParser objectParser;
//
//        Second(ObjectParser objectParser) {
//            this.objectParser = objectParser;
//        }
//
//        @Override
//        public void run() {
//            Message newMessage = null;
//            try {
//                newMessage = (Message) objectParser.take(Message.class);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(newMessage);
//        }
//    }

}
