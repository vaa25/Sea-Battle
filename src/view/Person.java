package view;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * @author Alexander Vlasov
 */
public class Person {
    private String name;
    private boolean online;
    private transient Text image;

    public Person() {
        this("Alex");
    }

    public Person(String name) {
        this.name = name;
        online = true;
//        image=new Text(name);
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getVisual() {
        if (image == null) {
            image = new Text(name);
        }
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!name.equals(person.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", online=" + online +
                '}';
    }
}
