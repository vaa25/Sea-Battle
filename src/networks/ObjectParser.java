package networks;

import common.Coord;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alexander Vlasov
 */
public class ObjectParser {
    private Map<Class, BlockingQueue> map;

    public ObjectParser() {
        this.map = new ConcurrentHashMap<>();
    }

    public Object take(Class clazz) throws InterruptedException {
        if (!hasClass(clazz)) {
            System.out.println(Thread.currentThread().getName() + " parser хочет take неизвестный " + clazz);
            if (putNewQueue(clazz)) {
                System.out.println(Thread.currentThread().getName() + " parser создал новый BlockingQueue для take нового " + clazz);
            } else
                System.out.println(Thread.currentThread().getName() + " parser не смог создать новый BlockingQueue для take нового " + clazz);
        }
        Object object = map.get(clazz).take();
        System.out.println(Thread.currentThread().getName() + " parser take " + object);
        return object;
    }

    public boolean hasClass(Class clazz) {
        return map.containsKey(clazz);
    }

    private synchronized boolean putNewQueue(Class clazz) {
        if (map.put(clazz, new LinkedBlockingQueue()) == null) {
            if (map.containsKey(clazz)) return true;
            else {
                System.out.println(Thread.currentThread().getName() + " Ошибка при попытке создать новый LinkedBlockingQueue для " + clazz);
                return false;
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " LinkedBlockingQueue для " + clazz + " уже был создан");
            return false;
        }
    }

    public void put(Object object) throws InterruptedException {
        Class clazz = object.getClass();
        if (!hasClass(clazz)) {
            System.out.println(Thread.currentThread().getName() + " parser put новый " + clazz + " для " + object);
            if (putNewQueue(clazz))
                System.out.println(Thread.currentThread().getName() + " parser создал новый BlockingQueue для put нового " + clazz + " объекта " + object);
            else
                System.out.println(Thread.currentThread().getName() + " parser не смог создать новый BlockingQueue для put нового " + clazz + " объекта " + object);
        }
        map.get(clazz).put(object);
        System.out.println(Thread.currentThread().getName() + " parser успешно put " + object);
    }

    public static void main(String[] args) throws InterruptedException {
        ObjectParser objectParser = new ObjectParser();
        Message message = new Message("blablabla");
        Coord coord = new Coord(2, 4);
        objectParser.put(message);
        objectParser.put(coord);


        new Thread(new First(objectParser)).start();
        new Thread(new Second(objectParser)).start();
    }

    static class First implements Runnable {
        private ObjectParser objectParser;

        First(ObjectParser objectParser) {
            this.objectParser = objectParser;
        }

        @Override
        public void run() {
            Coord newCoord = null;
            try {
                newCoord = (Coord) objectParser.take(Coord.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(newCoord);
        }
    }

    static class Second implements Runnable {
        private ObjectParser objectParser;

        Second(ObjectParser objectParser) {
            this.objectParser = objectParser;
        }

        @Override
        public void run() {
            Message newMessage = null;
            try {
                newMessage = (Message) objectParser.take(Message.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(newMessage);
        }
    }

}
